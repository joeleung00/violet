package com.learn.translation.deepLearnEnglish.controller;

import com.learn.translation.deepLearnEnglish.model.dto.ExplainationDto;
import com.learn.translation.deepLearnEnglish.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("translate")
public class VocabTranslationController {
    @Autowired
    TranslationService translationService;

    @GetMapping("/{word}")
    public ResponseEntity<ExplainationDto> translate(@PathVariable String word){
        List<String> explainations = translationService.translateVocab(word);
        ExplainationDto explainDto = new ExplainationDto(explainations);
        return new ResponseEntity<>(explainDto, HttpStatus.OK);
    }
}
