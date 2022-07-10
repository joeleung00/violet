package com.learn.translation.violet.model.dto;

import com.learn.translation.violet.model.entity.Article;
import com.learn.translation.violet.model.entity.Vocab;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class VocabDto {
    private Long id;
    private String vocab;
    private ExplainationDto explainationDto;
    private String sentenceEng;
    private String sentenceChin;
    private ArticleTitleDto articleTitleDto;

    private Date createTime;

    public VocabDto(Vocab vocab) {
        this.id =  vocab.getId();
        this.vocab = vocab.getVocab();
        List<String> output = Arrays.asList(vocab.getExplainations().trim().split("\n"));

        this.explainationDto = new ExplainationDto(output);

        this.sentenceEng = vocab.getSentenceEng();
        this.sentenceChin = vocab.getSentenceChin();

        Article article = vocab.getArticle();
        this.articleTitleDto = new ArticleTitleDto(article);

        this.createTime = vocab.getCreateTime();
    }
}
