package com.learn.translation.violet.model.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Data
@Table(name = "article")
@TypeDef(
        name = "string_array",
        typeClass = StringArrayType.class
)
public class Article extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_eng", nullable = false)
    private String titleEng;

    @Type(type = "string_array")
    @Column(
            name = "body_eng",
            columnDefinition = "TEXT[]",
            nullable = false
    )
    private String[] bodyEng;

    @Column(name = "title_chin", nullable = false)
    private String titleChin;

    @Type(type = "string_array")
    @Column(
            name = "body_chin",
            columnDefinition = "TEXT[]",
            nullable = false
    )
    private String[] bodyChin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}