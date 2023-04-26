package com.wonjiyap.pantryfairyapp.dto.category;

import com.wonjiyap.pantryfairyapp.domain.Category;
import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    private String name;

    public CategoryDto(Category category) {
        id = category.getId();
        name = category.getName();
    }
}
