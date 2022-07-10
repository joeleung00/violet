package com.learn.translation.violet.model.dto;

import com.learn.translation.violet.model.entity.Article;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleTitleDto {
    private Long id;
    private String title;

    private Date createTime;

    public ArticleTitleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitleEng();
        this.createTime = article.getCreateTime();
    }
}
