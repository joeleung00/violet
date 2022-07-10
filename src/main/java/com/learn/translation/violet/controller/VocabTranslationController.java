package com.learn.translation.violet.controller;

import com.learn.translation.violet.model.dto.ExplainationDto;
import com.learn.translation.violet.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/translate")
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
