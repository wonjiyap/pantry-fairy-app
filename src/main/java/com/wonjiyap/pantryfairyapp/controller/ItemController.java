package com.wonjiyap.pantryfairyapp.controller;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.dto.category.CategoryDto;
import com.wonjiyap.pantryfairyapp.dto.category.UpdateCategoryRequest;
import com.wonjiyap.pantryfairyapp.dto.item.CreateItemRequest;
import com.wonjiyap.pantryfairyapp.dto.item.CreateItemResponse;
import com.wonjiyap.pantryfairyapp.dto.item.ItemDto;
import com.wonjiyap.pantryfairyapp.dto.item.UpdateItemRequest;
import com.wonjiyap.pantryfairyapp.service.CategoryService;
import com.wonjiyap.pantryfairyapp.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/api/v1/items")
    public List<ItemDto> getItems(@RequestParam Optional<String> name) {
        List<Item> items = itemService.findSearchedItems(name.orElse(null));
        return items.stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/items")
    public CreateItemResponse saveItem(@RequestBody @Valid CreateItemRequest request) {
        Category category = categoryService.findOne(request.getCategoryId());
        Item item = new Item(request.getName(),
                request.getDescription(),
                request.getStore(),
                request.getQuantity(),
                category);
        item.setIsActive(request.getIsActive());
        Long id = itemService.saveItem(item);
        return new CreateItemResponse(id);
    }

    @GetMapping("/api/v1/items/{itemId}")
    public ItemDto getItem(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findOne(itemId);
        return new ItemDto(item);
    }

    @PatchMapping("/api/v1/items/{itemId}")
    public ItemDto updateItem(@PathVariable("itemId") Long itemId,
                                      @RequestBody @Valid UpdateItemRequest request) {
        itemService.updateItem(itemId, request);
        Item item = itemService.findOne(itemId);
        return new ItemDto(item);
    }

    @DeleteMapping("/api/v1/items/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findOne(itemId);
        itemService.deleteOne(item);
    }
}
