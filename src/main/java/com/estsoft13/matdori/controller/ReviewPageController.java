package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.ReviewImage;
import com.estsoft13.matdori.dto.RestaurantResponseDto;
import com.estsoft13.matdori.dto.ReviewResponseDto;
import com.estsoft13.matdori.repository.ReviewImageRepository;
import com.estsoft13.matdori.repository.ReviewRepository;
import com.estsoft13.matdori.service.RestaurantService;
import com.estsoft13.matdori.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewPageController {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final ReviewImageRepository reviewImageRepository;

    // review post - test용 컨트롤러입니다.
    @GetMapping("/add-review")
    public String showCreateReviewForm(Model model) {
        List<Restaurant> restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);

        model.addAttribute("review", new Review());


        return "review-form";
    }

    @GetMapping("/review/{reviewId}")
    public String showReviewDetail(@PathVariable Long reviewId, Model model) {
        //Review review = reviewService.findById(reviewId);
        ReviewResponseDto responseDto = reviewService.findById(reviewId);
        model.addAttribute("review", responseDto);

        List<ReviewImage> images = reviewImageRepository.findAllByReviewId(reviewId);
        model.addAttribute("images", images);
        return "review-detail-test";
    }
}
