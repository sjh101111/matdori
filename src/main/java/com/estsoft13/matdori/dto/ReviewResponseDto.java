package com.estsoft13.matdori.dto;


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

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();
    }
}
