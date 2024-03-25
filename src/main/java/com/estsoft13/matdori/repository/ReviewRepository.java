package com.estsoft13.matdori.repository;

import com.estsoft13.matdori.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
