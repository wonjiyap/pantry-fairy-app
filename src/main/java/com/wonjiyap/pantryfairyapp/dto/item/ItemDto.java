package com.wonjiyap.pantryfairyapp.dto.item;

import com.wonjiyap.pantryfairyapp.domain.Category;
import com.wonjiyap.pantryfairyapp.domain.Item;
import com.wonjiyap.pantryfairyapp.dto.category.CategoryDto;
import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private String store;
    private Integer quantity;
    private Boolean isActive;
    private CategoryDto category;

    public ItemDto(Item item) {
        id = item.getId();
        name = item.getName();
        description = item.getDescription();
        store = item.getStore();
        quantity = item.getQuantity();
        isActive = item.getIsActive();
        category = new CategoryDto(item.getCategory());
    }
}
