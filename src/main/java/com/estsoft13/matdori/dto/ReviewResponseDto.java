package com.estsoft13.matdori.dto;


import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private String title;
    private String content;
    private Double rating;
    private LocalDateTime createdAt;
    private Restaurant restaurant;
    private int waitingTime;
    private String visitTime;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.restaurant = review.getRestaurant();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.waitingTime = review.getWaitingTime();
        this.visitTime = review.getVisitTime();
        this.createdAt = review.getCreatedAt();
    }
}
