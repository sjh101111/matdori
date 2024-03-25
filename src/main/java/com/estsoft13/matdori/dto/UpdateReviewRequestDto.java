package com.estsoft13.matdori.dto;

import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {
    private String title;
    private String content;
    private Double rating;

    public UpdateReviewRequestDto(String title, String content, Double rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }
}
