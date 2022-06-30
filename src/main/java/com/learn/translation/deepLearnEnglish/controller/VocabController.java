package com.learn.translation.deepLearnEnglish.controller;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.dto.VocabDto;
import com.learn.translation.deepLearnEnglish.model.entity.Vocab;
import com.learn.translation.deepLearnEnglish.model.param.VocabParam;
import com.learn.translation.deepLearnEnglish.service.ArticleService;
import com.learn.translation.deepLearnEnglish.service.TranslationService;
import com.learn.translation.deepLearnEnglish.service.VocabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/vocab")
public class VocabController {

    @Autowired
    private VocabService vocabService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TranslationService translationService;

    @GetMapping
    public ResponseEntity<Page<VocabDto>> getAllVocabByUser(
            @PageableDefault(sort = {"createTime"}, direction = DESC) Pageable pageable,
            @RequestParam Long userId) {
        Page<Vocab> vocabs = vocabService.getAllVocabByUserId(userId, pageable);
        List<VocabDto> vocabDtos = vocabs.getContent().stream()
                                    .map(v -> new VocabDto(v)).collect(Collectors.toList());
        Page<VocabDto> page = new PageImpl(vocabDtos, vocabs.getPageable(), vocabs.getTotalElements());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createVocab(@RequestBody @Valid VocabParam vocabParam, @RequestParam Long userId) {
        vocabParam.setArticleService(articleService);
        vocabParam.setTranslationService(translationService);
        ApiResponse response = vocabService.createVocab(vocabParam, userId);
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return new ResponseEntity(response, status);
    }

    @DeleteMapping("/{vocabId}")
    public ResponseEntity<ApiResponse> deleteVocab(@PathVariable Long vocabId, @RequestParam Long userId){
        ApiResponse response = vocabService.deleteVocab(vocabId, userId);
        HttpStatus status = response.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

}
