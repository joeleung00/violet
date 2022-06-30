package com.learn.translation.deepLearnEnglish.model.param;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learn.translation.deepLearnEnglish.model.dto.TextPair;
import com.learn.translation.deepLearnEnglish.model.entity.Article;
import com.learn.translation.deepLearnEnglish.model.entity.Category;
import com.learn.translation.deepLearnEnglish.service.CategoryService;
import com.learn.translation.deepLearnEnglish.service.TranslationService;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class ArticleParam {
    @JsonIgnore
    private CategoryService categoryService;

    @JsonIgnore
    private TranslationService translationService;
    @NotBlank
    private String title;

    @NotNull
    @Size(min = 1)
    private List<@NotBlank String> body;

    private Long categoryId;

    public static String joinTexts(List<String> texts) {
        return String.join("\n\n", texts);
    }

    public Article convertToAritcle(Long userId) {
        Article article = new Article();
        article.setTitleEng(this.title);
        this.setTitleChin(article);
        this.setBodyAndBodyChin(article);
        if (categoryId != null){
            Category category = categoryService.getCategoryByIdAndUser(this.categoryId, userId).get();
            article.setCategory(category);
        }
        article.setUserId(userId);
        return article;
    }

    @JsonIgnore
    public boolean isCategoryIdValid(Long userId){
        if (categoryId != null){
            Optional<Category> optional = categoryService.getCategoryByIdAndUser(this.categoryId, userId);
            return optional.isPresent();
        }
        return true;
    }
    @JsonIgnore
    private void setTitleChin(Article article) {
        article.setTitleChin(translationService.translateText(this.title).getTextChin());
    }

    @JsonIgnore
    private void setBodyAndBodyChin(Article article) {
        List<TextPair> textPairs = translationService.translateTexts(this.body);
        String[][] bodyEng = textPairs.stream().map(pair -> pair.getTextEng().split("\n")).toArray(String[][]::new);
        article.setBodyEng(bodyEng);

        String[][] bodyChin = textPairs.stream().map(pair -> pair.getTextChin().split("\n")).toArray(String[][]::new);
        article.setBodyChin(bodyChin);
    }



}
