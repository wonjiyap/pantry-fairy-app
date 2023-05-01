package com.wonjiyap.pantryfairyapp.dto.item;

import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.dto.category.CategoryDto;
import lombok.Data;
import org.springframework.data.history.Revision;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private String store;
    private Integer quantity;
    private Boolean isActive;
    private CategoryDto category;
    private List<ItemHistoryDto> histories;

    public ItemDto(Item item, List<Revision<Integer, Item>> revisions) {
        id = item.getId();
        name = item.getName();
        description = item.getDescription();
        store = item.getStore();
        quantity = item.getQuantity();
        isActive = item.getIsActive();
        category = new CategoryDto(item.getCategory());
        if (revisions != null) {
            histories = revisions.stream()
                    .map(ItemHistoryDto::new)
                    .collect(Collectors.toList());
        }
    }
}
