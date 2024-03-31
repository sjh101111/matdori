package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.ReviewImage;
import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.dto.ReviewResponseDto;
import com.estsoft13.matdori.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewPageController {
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;
    private final CommentService commentService;
    private final UserService userService;

    // review post - test용 컨트롤러입니다.
    @GetMapping("/add-review")
    public String showCreateReviewForm(@RequestParam(required = false) Long reviewId, Model model) {
        List<Restaurant> restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);

        if(reviewId == null) {
            model.addAttribute("review", new Review());
        } else {
            Review review = reviewService.findById(reviewId);
            model.addAttribute("review", review);
        }

        return "review-form";
    }

    @GetMapping("/review/{reviewId}")
    public String showReviewDetail(@PathVariable Long reviewId, Model model, @AuthenticationPrincipal User user) {
        //Review review = reviewService.findById(reviewId);
        Review review = reviewService.findById(reviewId);
        model.addAttribute("review", review);

        List<ReviewImage> images = reviewImageService.findAllByReviewId(reviewId);
        model.addAttribute("images", images);

        List<CommentResponseDto> comments = commentService.getAllCommentsOfReview(reviewId);
        model.addAttribute("comments", comments);

        // 현재 로그인한 유저가 글을 등록한 유저인지 확인 후 글을 수정할 수 있게끔
        Long userId = user.getId();
        boolean isOwner = review.getUser().getId().equals(userId);
        model.addAttribute("isOwner", isOwner);

        return "detailedReviewPage";
    }
}
