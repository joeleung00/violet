package com.learn.translation.deepLearnEnglish.service;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.entity.Category;
import com.learn.translation.deepLearnEnglish.model.param.CategoryParam;
import com.learn.translation.deepLearnEnglish.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategoriesByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Optional<Category> getCategoryByIdAndUser(Long id, Long userId) {
        return categoryRepository.findByIdAndUserId(id, userId);
    }

    //TODO: Handle duplication
   public ApiResponse createCategory(CategoryParam categoryParam, Long userId) {
        if (categoryRepository.existsByUserIdAndName(userId, categoryParam.getName())){
            return new ApiResponse(false, "Category Name already Exist");
        }
        Category category = categoryParam.convertToCategory(userId);
        categoryRepository.save(category);
        return new ApiResponse(true, "Category is created");
   }

   public ApiResponse deleteCategory(Long categoryId, Long userId){
        Optional<Category> optionalCategory = categoryRepository.findByIdAndUserId(categoryId, userId);
        if (!optionalCategory.isPresent()){
            return new ApiResponse(false, "Invalid category id");
        }
        categoryRepository.deleteById(categoryId);
        return new ApiResponse(true, "Category was deleted");
   }

}
