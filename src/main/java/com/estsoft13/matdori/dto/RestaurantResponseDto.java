package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private String category;
    private Double avgRating;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.category = restaurant.getCategory();
        this.avgRating = restaurant.getAvgRating();
    }
}
