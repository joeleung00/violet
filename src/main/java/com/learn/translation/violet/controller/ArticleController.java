package com.learn.translation.violet.controller;

import com.learn.translation.violet.common.ApiResponse;
import com.learn.translation.violet.model.dto.ArticleDto;
import com.learn.translation.violet.model.dto.ArticleTitleDto;
import com.learn.translation.violet.model.entity.Article;
import com.learn.translation.violet.model.param.ArticleParam;
import com.learn.translation.violet.security.CustomUserDetails;
import com.learn.translation.violet.service.ArticleService;
import com.learn.translation.violet.service.CategoryService;
import com.learn.translation.violet.service.TranslationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public ResponseEntity<Page<ArticleTitleDto>> getArticlesByUser(
            @PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
            @RequestParam(required = false) Long categoryId,
            Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Page<Article> articles;
        if (categoryId == null){
            articles = articleService.getArticleByUserId(user.getId(), pageable);
        }
        else {
            articles = articleService.getArticleByUserIdAndCategoryId(user.getId(), pageable, categoryId);
        }
        List<ArticleTitleDto> articleTitleDtos = articles.getContent().stream().map(a -> new ArticleTitleDto(a)).collect(Collectors.toList());
        Page<ArticleTitleDto> output = new PageImpl(articleTitleDtos, articles.getPageable(), articles.getTotalElements());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Object> getArticleDetailsByUser(@PathVariable Long articleId, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Optional<Article> optionalArticle = articleService.getArticleByIdAndUserId(articleId, user.getId());
        if (optionalArticle.isEmpty()){
            return new ResponseEntity<> (new ApiResponse(false, "Invalid article Id"), HttpStatus.BAD_REQUEST);
        }
        ArticleDto articleDto = new ArticleDto(optionalArticle.get());
        return new ResponseEntity<> (articleDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createArticle(@RequestBody @Valid ArticleParam articleParam, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        articleParam.setCategoryService(categoryService);
        articleParam.setTranslationService(translationService);
        ApiResponse response = articleService.createArticle(articleParam, user.getId());
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity<ApiResponse> deleteArticle(@PathVariable Long articleId, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        ApiResponse response = articleService.deleteArticle(articleId, user.getId());
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

}
