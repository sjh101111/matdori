package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Restaurant;

public class UpdateRestResponseDto {
    private String name;
    private String address;
    private String category;

    public UpdateRestResponseDto (Restaurant restaurant) {
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.category = restaurant.getCategory();
    }
}
