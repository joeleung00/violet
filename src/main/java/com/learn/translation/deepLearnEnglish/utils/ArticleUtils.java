package com.learn.translation.deepLearnEnglish.utils;

import com.learn.translation.deepLearnEnglish.model.dto.TextPair;
import com.learn.translation.deepLearnEnglish.model.entity.Article;

import java.util.Optional;

public class ArticleUtils {

    public static Optional<TextPair> searchSentenceByWord(String word, Article article) {
        String[][] bodyEng = article.getBodyEng();
        String[][] bodyChin = article.getBodyChin();

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
}
