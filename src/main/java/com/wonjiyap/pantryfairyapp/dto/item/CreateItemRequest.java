package com.wonjiyap.pantryfairyapp.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wonjiyap.pantryfairyapp.domain.Category;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateItemRequest {

    @NotBlank
    private String name;
    private String description;
    private String store;
    private Integer quantity;
    private Boolean isActive;
    @NotNull
    private Long categoryId;
}
