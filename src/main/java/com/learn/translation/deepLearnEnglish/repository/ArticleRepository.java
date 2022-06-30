package com.learn.translation.deepLearnEnglish.repository;

import com.learn.translation.deepLearnEnglish.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository <Article, Long> {
    Optional<Article> findByIdAndUserId(Long id, Long userId);

    Page<Article> findByUserId(Long userId, Pageable pageable);

    Page<Article> findByCategoryIdAndUserId(Long categoryId, Long userId, Pageable pageable);

}
