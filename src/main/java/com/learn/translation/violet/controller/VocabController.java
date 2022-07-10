package com.learn.translation.violet.controller;

import com.learn.translation.violet.common.ApiResponse;
import com.learn.translation.violet.model.dto.VocabDto;
import com.learn.translation.violet.model.entity.Vocab;
import com.learn.translation.violet.model.param.VocabParam;
import com.learn.translation.violet.security.CustomUserDetails;
import com.learn.translation.violet.service.ArticleService;
import com.learn.translation.violet.service.TranslationService;
import com.learn.translation.violet.service.VocabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping(path = "/api/v1/vocab")
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
            @RequestParam(required = false) Long articleId,
            Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Page<Vocab> vocabs;
        if (articleId == null){
            vocabs = vocabService.getAllVocabByUserId(user.getId(), pageable);
        }
        else {
            pageable = PageRequest.of(0, 200); // frontend need to highlight all the vocab for specific article, default size 10 cannot include all vocab.
            vocabs = vocabService.getVocabByUserIdAndArticleId(user.getId(), articleId, pageable);
        }
        List<VocabDto> vocabDtos = vocabs.getContent().stream()
                                    .map(v -> new VocabDto(v)).collect(Collectors.toList());
        Page<VocabDto> page = new PageImpl(vocabDtos, vocabs.getPageable(), vocabs.getTotalElements());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createVocab(@RequestBody @Valid VocabParam vocabParam, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        vocabParam.setArticleService(articleService);
        vocabParam.setTranslationService(translationService);
        ApiResponse response = vocabService.createVocab(vocabParam, user.getId());
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return new ResponseEntity(response, status);
    }

    @DeleteMapping("/{vocabId}")
    public ResponseEntity<ApiResponse> deleteVocab(@PathVariable Long vocabId, Authentication authentication){
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        ApiResponse response = vocabService.deleteVocab(vocabId, user.getId());
        HttpStatus status = response.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

}
