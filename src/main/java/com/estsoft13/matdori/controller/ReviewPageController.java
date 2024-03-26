package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.dto.RestaurantResponseDto;
import com.estsoft13.matdori.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewPageController {
    private final RestaurantService restaurantService;

    @GetMapping("/add-review")
    public String showCreateReviewForm(Model model) {
        List<Restaurant> restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);

        model.addAttribute("review", new Review());


        return "review-form";
    }
}
