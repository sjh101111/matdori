package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.dto.AddRestaurantRequestDto;
import com.estsoft13.matdori.dto.RestaurantResponseDto;
import com.estsoft13.matdori.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 모든 식당 조회
    @GetMapping("/api/restaurants")
    public List<RestaurantResponseDto> getRestaurants() {
        return restaurantService.getRestaurants();
    }

    //식당 하나 조회
    @GetMapping("/api/restaurant/{restaurantId}")
    public RestaurantResponseDto getRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurant(restaurantId);
    }
    // 식당 추가
    @PostMapping("/api/restaurant")
    public RestaurantResponseDto createRestaurant(@RequestBody AddRestaurantRequestDto requestDto) {
        return restaurantService.createRestaurant(requestDto);
    }
}
