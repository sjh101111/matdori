package com.estsoft13.matdori.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddReviewRequestDto {
    private String title;
    private String content;
    private Double rating;
    private Long restaurantId;
    private int waitingTime;
    private String visitTime;
}
