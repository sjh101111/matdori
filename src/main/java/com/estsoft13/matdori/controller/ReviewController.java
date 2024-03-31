package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.dto.*;
import com.estsoft13.matdori.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    // html에서 json으로 받지 않고 form-data를 생성해 전달
    // 이미지가 없어도 리뷰 등록 가능
    @PostMapping("/api/review")
    public ReviewResponseDto createReview(@ModelAttribute AddReviewRequestDto addReviewRequestDto,
                                          @RequestParam(value = "imgFiles", required = false) List<MultipartFile> imgFiles) {

        return reviewService.createReview(addReviewRequestDto, addReviewRequestDto.getRestaurantId(), imgFiles);
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
    /*
    @PutMapping("/api/review/{reviewId}")
    public UpdateReviewResponseDto updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewRequestDto requestDto) {
        return reviewService.updateReview(reviewId, requestDto);
    }
     */
    @PutMapping("/api/review/{reviewId}")
    public UpdateReviewResponseDto updateReview(@PathVariable Long reviewId, @ModelAttribute UpdateReviewRequestDto requestDto,
                                                @RequestParam(value = "imgFiles", required = false) List<MultipartFile> imgFiles) {
        return reviewService.updateReview(reviewId, requestDto, imgFiles);
    }

}
