package com.learn.translation.deepLearnEnglish.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
