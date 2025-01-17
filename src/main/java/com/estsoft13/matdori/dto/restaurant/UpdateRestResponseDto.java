package com.estsoft13.matdori.dto.restaurant;

import com.estsoft13.matdori.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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
