package com.learn.translation.deepLearnEnglish.repository;

import com.learn.translation.deepLearnEnglish.model.entity.Vocab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VocabRepository extends JpaRepository <Vocab, Long> {
    Page<Vocab> findByUserId(Long userId, Pageable pageable);

    Optional<Vocab> findByIdAndUserId(Long id, Long userId);
}
