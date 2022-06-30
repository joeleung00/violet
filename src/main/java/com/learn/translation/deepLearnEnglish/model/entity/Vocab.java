package com.learn.translation.deepLearnEnglish.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Vocab extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String vocab;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String explainations;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sentenceEng;

    @Column(name = "sentence_chin", nullable = false, columnDefinition = "TEXT")
    private String sentenceChin;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
}
