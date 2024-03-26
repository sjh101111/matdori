package com.estsoft13.matdori.repository;

import com.estsoft13.matdori.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
