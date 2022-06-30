package com.learn.translation.deepLearnEnglish.service;

import com.learn.translation.deepLearnEnglish.common.ApiResponse;
import com.learn.translation.deepLearnEnglish.model.entity.Vocab;
import com.learn.translation.deepLearnEnglish.model.param.VocabParam;
import com.learn.translation.deepLearnEnglish.repository.VocabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VocabService {
    @Autowired
    private VocabRepository vocabRepository;

    public Page<Vocab> getAllVocabByUserId(Long userId, Pageable pageable){
        return vocabRepository.findByUserId(userId, pageable);
    }

    public ApiResponse createVocab(VocabParam vocabParam, Long userId){
        if (!vocabParam.isArticleIdValid(userId)) {
            return new ApiResponse(false, "Invalid article id");
        }
        Vocab vocab = vocabParam.convertToVocab(userId);
        vocabRepository.save(vocab);
        return new ApiResponse(true, "Vocab was created");
    }

    public ApiResponse deleteVocab(Long vocabId, Long userId) {
        Optional<Vocab> optionalVocab = vocabRepository.findByIdAndUserId(vocabId, userId);
        if (optionalVocab.isEmpty()){
            return new ApiResponse(false, "Invalid vocab id");
        }
        vocabRepository.delete(optionalVocab.get());
        return new ApiResponse(true, "Vocab was deleted");
    }
}
