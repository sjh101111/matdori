package com.estsoft13.matdori.repository;

import com.estsoft13.matdori.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurantId(Long restaurantId);

    List<Review> findByTitleContainingOrContentContainingOrRestaurantNameContainingOrRestaurantCategoryContaining(String keyword, String keyword1, String keyword2, String keyword3);

    List<Review> findAllByOrderByCreatedAtDesc();

    List<Review> findAllByOrderByRatingDesc();

    List<Review> findAllByOrderByViewCountDesc();

    List<Review> findAllByUserId(Long userId);
}
