package com.learn.translation.violet.controller;

import com.learn.translation.violet.common.ApiResponse;
import com.learn.translation.violet.model.dto.CategoryDto;
import com.learn.translation.violet.model.entity.Category;
import com.learn.translation.violet.model.param.CategoryParam;
import com.learn.translation.violet.security.CustomUserDetails;
import com.learn.translation.violet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategoryByUser(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        List<Category> categories = categoryService.getCategoriesByUserId(user.getId());
        List<CategoryDto> categoryDtos = categories.stream()
                .map(cat -> new CategoryDto(cat)).collect(Collectors.toList());
        return new ResponseEntity(categoryDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody @Valid CategoryParam categoryParam, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        ApiResponse response = categoryService.createCategory(categoryParam, user.getId());
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        ApiResponse response = categoryService.deleteCategory(categoryId, user.getId());
        HttpStatus status = response.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return new ResponseEntity(response, status);
    }
}
