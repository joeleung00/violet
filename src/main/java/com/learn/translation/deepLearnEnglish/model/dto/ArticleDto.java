package com.learn.translation.deepLearnEnglish.model.dto;

import com.learn.translation.deepLearnEnglish.model.entity.Article;
import com.learn.translation.deepLearnEnglish.model.entity.Category;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ArticleDto {
    private Long id;

    private String titleEng;

    private List<String> bodyEng;
    private String titleChin;
    private List<String> bodyChin;
    private Long categoryId;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.titleEng = article.getTitleEng();
        this.titleChin = article.getTitleChin();
        Optional<Category> optionalCategory = Optional.ofNullable(article.getCategory());
        if (optionalCategory.isPresent()){
            this.categoryId = optionalCategory.get().getId();
        }
        else {
            this.categoryId = null;
        }

        this.bodyEng = Arrays.asList(article.getBodyEng()).stream()
                .map(sens -> String.join(" ", sens)).collect(Collectors.toList());
        this.bodyChin = Arrays.asList(article.getBodyChin()).stream()
                .map(sens -> String.join(" ", sens)).collect(Collectors.toList());
    }
}
