package com.learn.translation.deepLearnEnglish.repository;

import com.learn.translation.deepLearnEnglish.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
    List<Category> findByUserId(Long userId);

    Optional<Category> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndName(Long userId, String name);
}
