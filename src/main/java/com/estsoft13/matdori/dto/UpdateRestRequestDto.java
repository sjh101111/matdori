package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRestRequestDto {
    private String name;
    private String address;
    private String category;

    public UpdateRestRequestDto(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.category = restaurant.getCategory();
    }
}
