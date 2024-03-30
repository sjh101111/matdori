package com.estsoft13.matdori.repository;

import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findAllByReviewId(Long reviewId);

    void deleteByReview(Review review);
}
