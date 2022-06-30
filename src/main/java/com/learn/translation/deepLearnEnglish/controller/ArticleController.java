package com.learn.translation.deepLearnEnglish.controller;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.dto.ArticleDto;
import com.learn.translation.deepLearnEnglish.model.entity.Article;
import com.learn.translation.deepLearnEnglish.model.param.ArticleParam;
import com.learn.translation.deepLearnEnglish.service.ArticleService;
import com.learn.translation.deepLearnEnglish.service.CategoryService;
import com.learn.translation.deepLearnEnglish.service.TranslationService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public ResponseEntity<Page<ArticleDto>> getArticlesByUser(
            @PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
            @RequestParam Long userId) {
        Page<Article> articles = articleService.getArticleByUserId(userId, pageable);
        List<ArticleDto> articleDtos = articles.getContent().stream().map(a -> new ArticleDto(a)).collect(Collectors.toList());
        Page<ArticleDto> output = new PageImpl(articleDtos, articles.getPageable(), articles.getTotalElements());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createArticle(@RequestBody @Valid ArticleParam articleParam, @RequestParam Long userId) {
        articleParam.setCategoryService(categoryService);
        articleParam.setTranslationService(translationService);
        ApiResponse response = articleService.createArticle(articleParam, userId);
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity<ApiResponse> deleteArticle(@PathVariable Long articleId, @RequestParam Long userId) {
        ApiResponse response = articleService.deleteArticle(articleId, userId);
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

}
