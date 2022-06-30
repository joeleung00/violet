package com.learn.translation.deepLearnEnglish.controller;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.dto.CategoryDto;
import com.learn.translation.deepLearnEnglish.model.entity.Category;
import com.learn.translation.deepLearnEnglish.model.param.CategoryParam;
import com.learn.translation.deepLearnEnglish.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategoryByUser(@RequestParam Long userId) {
        List<Category> categories = categoryService.getCategoriesByUserId(userId);
        List<CategoryDto> categoryDtos = categories.stream()
                .map(cat -> new CategoryDto(cat)).collect(Collectors.toList());
        return new ResponseEntity(categoryDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody @Valid CategoryParam categoryParam, @RequestParam Long userId) {
        ApiResponse response = categoryService.createCategory(categoryParam, userId);
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId, @RequestParam Long userId) {
        ApiResponse response = categoryService.deleteCategory(categoryId, userId);
        HttpStatus status = response.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return new ResponseEntity(response, status);
    }
}
