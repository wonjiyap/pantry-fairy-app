package com.wonjiyap.pantryfairyapp.controller;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.dto.category.CategoryDto;
import com.wonjiyap.pantryfairyapp.dto.category.CreateCategoryRequest;
import com.wonjiyap.pantryfairyapp.dto.category.CreateCategoryResponse;
import com.wonjiyap.pantryfairyapp.dto.category.UpdateCategoryRequest;
import com.wonjiyap.pantryfairyapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/v1/categories")
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryService.findCategories();
        return categories.stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/v1/categories")
    public CreateCategoryResponse saveCategory(@RequestBody @Valid CreateCategoryRequest request) {
        Category category = new Category(request.getName());
        Long id = categoryService.saveCategory(category);
        return new CreateCategoryResponse(id);
    }

    @GetMapping("/api/v1/categories/{categoryId}")
    public CategoryDto getCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findOne(categoryId);
        return new CategoryDto(category);
    }

    @PutMapping("/api/v1/categories/{categoryId}")
    public CategoryDto updateCategory(@PathVariable("categoryId") Long categoryId,
                                      @RequestBody @Valid UpdateCategoryRequest request) {
        categoryService.updateCategory(categoryId, request.getName());
        Category category = categoryService.findOne(categoryId);
        return new CategoryDto(category);
    }

    @DeleteMapping("/api/v1/categories/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findOne(categoryId);
        categoryService.deleteOne(category);
    }
}
