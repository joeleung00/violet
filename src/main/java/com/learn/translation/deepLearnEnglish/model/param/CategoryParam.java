package com.learn.translation.deepLearnEnglish.model.param;

import com.learn.translation.deepLearnEnglish.model.entity.Category;
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
