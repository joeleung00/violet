package com.learn.translation.deepLearnEnglish.model.dto;

import com.learn.translation.deepLearnEnglish.model.entity.Article;
import lombok.Data;

@Data
public class ArticleTitleDto {
    private Long id;
    private String title;

    public ArticleTitleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitleEng();
    }
}
