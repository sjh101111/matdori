package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.ReviewImage;
import com.estsoft13.matdori.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    // 리뷰 이미지 저장
    @Transactional
    public List<ReviewImage> findAllByReviewId(Long reviewId) {
        List<ReviewImage> images = reviewImageRepository.findAllByReviewId(reviewId);

        return images;
    }
}
