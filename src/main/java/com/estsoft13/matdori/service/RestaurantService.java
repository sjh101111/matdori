package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.dto.AddRestaurantRequestDto;
import com.estsoft13.matdori.dto.RestaurantResponseDto;
import com.estsoft13.matdori.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public List<RestaurantResponseDto> getRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(RestaurantResponseDto::new)
                .toList();
    }

    @Transactional
    public RestaurantResponseDto createRestaurant(AddRestaurantRequestDto requestDto) {

        Restaurant restaurant = new Restaurant(requestDto);
        restaurantRepository.save(restaurant);
        return new RestaurantResponseDto(restaurant);
    }

    @Transactional
    public RestaurantResponseDto getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(RestaurantResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("식당 ID가 존재하지 않습니다."));
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
}
