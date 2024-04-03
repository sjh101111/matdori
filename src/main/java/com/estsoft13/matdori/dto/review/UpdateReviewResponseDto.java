package com.estsoft13.matdori.dto.review;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateReviewResponseDto {
    private Long id;
    private String title;
    private String content;
    private Double rating;
    private LocalDateTime createdAt;
    private Long restaurantId;
    private int waitingTime;
    private String visitTime;
    private List<String> imgPaths = new ArrayList<>();

    public UpdateReviewResponseDto(Review review) {
        this.id = review.getId();
        this.restaurantId = review.getRestaurant().getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.waitingTime = review.getWaitingTime();
        this.visitTime = review.getVisitTime();
        this.createdAt = review.getCreatedAt();
    }
}
