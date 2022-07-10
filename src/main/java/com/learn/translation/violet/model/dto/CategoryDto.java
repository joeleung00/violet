package com.learn.translation.violet.model.dto;

import com.learn.translation.violet.model.entity.Category;
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
