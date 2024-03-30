package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.ReviewImage;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.dto.ReviewResponseDto;
import com.estsoft13.matdori.service.CommentService;
import com.estsoft13.matdori.service.RestaurantService;
import com.estsoft13.matdori.service.ReviewImageService;
import com.estsoft13.matdori.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewPageController {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;
    private final CommentService commentService;

    // review post - test용 컨트롤러입니다.
    @GetMapping("/add-review")
    public String showCreateReviewForm(@RequestParam(required = false) Long reviewId, Model model) {
        List<Restaurant> restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);

        if(reviewId == null) {
            model.addAttribute("review", new Review());
        } else {
            ReviewResponseDto review = reviewService.findById(reviewId);
            model.addAttribute("review", review);
        }

        return "review-form";
    }

    @GetMapping("/review/{reviewId}")
    public String showReviewDetail(@PathVariable Long reviewId, Model model) {
        //Review review = reviewService.findById(reviewId);
        ReviewResponseDto responseDto = reviewService.findById(reviewId);
        model.addAttribute("review", responseDto);

        List<ReviewImage> images = reviewImageService.findAllByReviewId(reviewId);
        model.addAttribute("images", images);


        List<CommentResponseDto> comments = commentService.getAllCommentsOfReview(reviewId);
        model.addAttribute("comments", comments);
        return "detailedReviewPage";
    }
}
