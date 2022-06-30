package com.learn.translation.deepLearnEnglish.model.dto;

import com.learn.translation.deepLearnEnglish.model.entity.Category;
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
