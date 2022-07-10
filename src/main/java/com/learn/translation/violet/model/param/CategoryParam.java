package com.learn.translation.violet.model.param;

import com.learn.translation.violet.model.entity.Category;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryParam {
    @NotBlank
    private String name;

    public Category convertToCategory(Long userId){
        Category cat = new Category();
        cat.setName(name);
        cat.setUserId(userId);
        return cat;
    }
}
