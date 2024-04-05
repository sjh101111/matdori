package com.estsoft13.matdori.controller.restaurant;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.dto.restaurant.RestaurantResponseDto;
import com.estsoft13.matdori.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestaurantPageController {

    private final RestaurantService restaurantService;

    // 식당 관리 페이지
    @GetMapping("/restaurants")
    public String showAllRestaurant(Model model) {

        List<RestaurantResponseDto> restaurantResponseDtoList = restaurantService.getRestaurants();
        model.addAttribute("restaurants", restaurantResponseDtoList);

        return "view-rest-list";
    }

    // 식당 추가, 수정 페이지
    @GetMapping("/add-restaurant")
    public String showCreateRestaurantForm(@RequestParam(required = false) Long restaurantId, Model model) {

        // 식당 추가
        if (restaurantId == null) {
            model.addAttribute("restaurant", new Restaurant());

            // 식당 수정
        } else {
            RestaurantResponseDto responseDto = restaurantService.getRestaurant(restaurantId);
            model.addAttribute("restaurant", responseDto);
        }

        return "rest-form";
    }
}
