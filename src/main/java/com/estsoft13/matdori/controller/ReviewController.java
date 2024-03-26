package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.dto.AddReviewRequestDto;
import com.estsoft13.matdori.dto.ReviewResponseDto;
import com.estsoft13.matdori.dto.UpdateReviewRequestDto;
import com.estsoft13.matdori.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 전체 조회
    @GetMapping("/api/reviews")
    public List<ReviewResponseDto> getReviews() {
        return reviewService.getReviews();
    }

    // 리뷰 게시
    @PostMapping("/api/review")
    public ReviewResponseDto createReview(@RequestBody AddReviewRequestDto requestDto) {
        return reviewService.createReview(requestDto, requestDto.getRestaurantId());
    }

    // 리뷰 하나 조회
    @GetMapping("/api/review/{reviewId}")
    public ReviewResponseDto getReview(@PathVariable Long reviewId) {
        return reviewService.getReview(reviewId);
    }

    // 리뷰 DELETE
    @DeleteMapping("/api/review/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
    }

    // 리뷰 UPDATE
    @PutMapping("/api/review/{reviewId}")
    public ReviewResponseDto updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewRequestDto requestDto) {
        return reviewService.updateReview(reviewId, requestDto);
    }
}
