package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Restaurant;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddReviewRequestDto {
    private String title;
    private String content;
    private Double rating;
    private Long restaurantId;
    private int waitingTime;
    private String visitTime;

}
