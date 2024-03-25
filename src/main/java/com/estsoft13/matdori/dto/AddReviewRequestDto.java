package com.estsoft13.matdori.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddReviewRequestDto {
    private String title;
    private String content;
    private Double rating;

}
