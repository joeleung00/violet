package com.learn.translation.deepLearnEnglish.service;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.dto.ArticleDto;
import com.learn.translation.deepLearnEnglish.model.entity.Article;
import com.learn.translation.deepLearnEnglish.model.param.ArticleParam;
import com.learn.translation.deepLearnEnglish.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public Optional<Article> getArticleByIdAndUserId(Long articleId, Long userId) {
        return articleRepository.findByIdAndUserId(articleId, userId);
    }

    public Page<Article> getArticleByUserId(Long userId, Pageable pageable) {
        return articleRepository.findByUserId(userId, pageable);
    }

    public Page<Article> getArticleByCategoryIdAndUserId(Long categoryId, Long userId, Pageable pageable) {
        return articleRepository.findByCategoryIdAndUserId(categoryId, userId, pageable);
    }

    public ApiResponse createArticle(ArticleParam articleParam, Long userId) {
        if (!articleParam.isCategoryIdValid(userId)) {
            return new ApiResponse(false, "Invalid category id");
        }
        Article article = articleParam.convertToAritcle(userId);
        articleRepository.save(article);
        return new ApiResponse(true, "Article is created");
    }

    public ApiResponse deleteArticle(Long articleId, Long userId) {
        Optional<Article> optionalArticle = getArticleByIdAndUserId(articleId, userId);
        if (!optionalArticle.isPresent()) {
            return new ApiResponse(false, "Invalid article id");
        }
        articleRepository.delete(optionalArticle.get());
        return new ApiResponse(true, "Article was deleted");
    }

}
