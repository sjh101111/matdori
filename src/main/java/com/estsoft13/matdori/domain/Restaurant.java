package com.estsoft13.matdori.domain;

import com.estsoft13.matdori.dto.AddRestaurantRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "avg_rating", nullable = false)
    private Double avgRating;

    public Restaurant(AddRestaurantRequestDto requestDto) {
        this.name = requestDto.getName();
        this.address = requestDto.getAddress();
        this.category = requestDto.getCategory();
        this.avgRating = requestDto.getAvgRating();
    }
}
