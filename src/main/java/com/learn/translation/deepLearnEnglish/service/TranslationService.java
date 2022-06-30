package com.learn.translation.deepLearnEnglish.service;

import com.learn.translation.deepLearnEnglish.grpc.TranslationGrpcClient;
import com.learn.translation.deepLearnEnglish.model.dto.TextPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {
    @Autowired
    private TranslationGrpcClient translationGrpcClient;

    public List<String> translateVocab(String vocab){
        String output = translationGrpcClient.translateVocab(vocab);
        if (output.isEmpty()){
            return Collections.emptyList();
        }
        return Arrays.asList(output.trim().split("\n"));
    }

    public String translateVocabReturnRaw(String vocab) {
        return translationGrpcClient.translateVocab(vocab);
    }

    public List<TextPair> translateTexts(List<String> texts) {
        return texts.stream().map(t -> translateText(t)).collect(Collectors.toList());
    }

    public TextPair translateText(String text) {
        return translationGrpcClient.translateText(text);
    }
}
