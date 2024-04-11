package com.estsoft13.matdori.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class AddRestaurantRequestDto {
    private String name;
    private String address;
    private String category;
    private Double avgRating = 0.0;
}
