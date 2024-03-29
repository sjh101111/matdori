package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.ReviewImage;
import com.estsoft13.matdori.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageService {
    private final ReviewImageRepository reviewImageRepository;

    public List<ReviewImage> findAllByReviewId(Long reviewId) {
        List<ReviewImage> images = reviewImageRepository.findAllByReviewId(reviewId);
        return images;
    }
}
