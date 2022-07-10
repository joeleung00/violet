package com.learn.translation.violet.utils;

import com.learn.translation.violet.model.dto.TextPair;
import com.learn.translation.violet.model.entity.Article;

import java.util.Arrays;
import java.util.Optional;

public class ArticleUtils {

    public static Optional<TextPair> searchSentenceByWord(String word, Article article) {
        String title = article.getTitleEng();
        if (title.contains(word)){
            TextPair textPair = new TextPair();
            textPair.setTextEng(article.getTitleEng());
            textPair.setTextChin(article.getTitleChin());
            return Optional.of(textPair);
        }


        String[][] bodyEng = splitAsSentences(article.getBodyEng());
        String[][] bodyChin = splitAsSentences(article.getBodyChin());

        int row = -1;
        int col = -1;
        for (int i = 0; i < bodyEng.length; i++) {
            for (int j = 0; j < bodyEng[i].length; j++) {
                String sen = bodyEng[i][j];
                if (sen.contains(word)) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        if (row >= 0) {
            TextPair pair = new TextPair();
            pair.setTextEng(bodyEng[row][col]);
            pair.setTextChin(bodyChin[row][col]);
            return Optional.of(pair);
        }
        return Optional.empty();
    }

    public static String[][] splitAsSentences(String[] paras){
        return Arrays.stream(paras)
                .map(para -> para.split("\n"))
                .toArray(String[][]::new);
    }

}
