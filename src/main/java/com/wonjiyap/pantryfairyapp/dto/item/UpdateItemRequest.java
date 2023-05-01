package com.wonjiyap.pantryfairyapp.dto.item;

import lombok.Data;

@Data
public class UpdateItemRequest {

    private String name;
    private String description;
    private String store;
    private Integer quantity;
    private Boolean isActive;
    private Long categoryId;
}
