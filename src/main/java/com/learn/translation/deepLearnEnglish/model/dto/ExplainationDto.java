package com.learn.translation.deepLearnEnglish.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExplainationDto {
    List<String> explainations;
    public ExplainationDto(List<String> explainations) {
        this.explainations = explainations;
    }
}
