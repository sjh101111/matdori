package com.estsoft13.matdori.dto.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRestaurantRequestDto {
    private String name;
    private String address;
    private String category;
    private Double avgRating = 0.0;
}
