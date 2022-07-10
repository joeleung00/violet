package com.learn.translation.violet.model.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.translation.violet.model.dto.TextPair;
import com.learn.translation.violet.model.entity.Article;
import com.learn.translation.violet.model.entity.Vocab;
import com.learn.translation.violet.service.ArticleService;
import com.learn.translation.violet.service.TranslationService;
import com.learn.translation.violet.utils.ArticleUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
public class VocabParam {
    @JsonIgnore
    private ArticleService articleService;

    @JsonIgnore
    private TranslationService translationService;

    @NotBlank
    @Size(max = 50)
    private String vocab;

    @NotNull
    private Long articleId;

    public Vocab convertToVocab(@NotNull Long userId) {
        String explainations = translationService.translateVocabReturnRaw(vocab);
        Vocab vocab = new Vocab();
        vocab.setVocab(this.vocab);
        vocab.setUserId(userId);

        Article article = articleService.getArticleByIdAndUserId(articleId, userId).get();
        vocab.setArticle(article);
        vocab.setExplainations(explainations);
        Optional<TextPair> optionalTextPair = ArticleUtils.searchSentenceByWord(this.vocab, article);
        if (optionalTextPair.isPresent()) {
            TextPair pair = optionalTextPair.get();
            vocab.setSentenceEng(pair.getTextEng());
            vocab.setSentenceChin(pair.getTextChin());
        }
        return vocab;
    }

    public boolean isArticleIdValid(Long userId) {
        Optional<Article> optionalArticle = articleService.getArticleByIdAndUserId(articleId, userId);
        return optionalArticle.isPresent();
    }
}
