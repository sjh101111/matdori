package com.estsoft13.matdori.dto;

import lombok.Getter;

@Getter
public class UpdateReviewRequestDto {
    private String title;
    private String content;
    private Double rating;
    private Long restaurantId;
    private int waitingTime;
    private String visitTime;

    public UpdateReviewRequestDto(String title, String content, Double rating,
                                  Long restaurantId, int waitingTime, String visitTime) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.restaurantId = restaurantId;
        this.waitingTime = waitingTime;
        this.visitTime = visitTime;
    }
}
