package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.ReviewImage;
import com.estsoft13.matdori.dto.*;
import com.estsoft13.matdori.repository.RestaurantRepository;
import com.estsoft13.matdori.repository.ReviewImageRepository;
import com.estsoft13.matdori.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewImageRepository reviewImageRepository;

    // 모든 리뷰 조회 서비스
    @Transactional
    public List<ReviewResponseDto> getReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDto::new)
                .toList();
    }

    // 리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(AddReviewRequestDto addReviewRequestDto, Long restaurantId,
                                          List<MultipartFile> imgFiles) {
        // 식당 찾기 및 예외 처리
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 식당이 존재하지 않습니다."));

        // 새 리뷰 객체 생성 및 저장
        Review review = new Review(addReviewRequestDto);
        review.setRestaurant(restaurant);  // Review 엔티티에 식당 설정

        ReviewResponseDto responseDto = new ReviewResponseDto(review);
        List<String> imgPaths = new ArrayList<>();

        if (imgFiles != null && !imgFiles.isEmpty()) {
            for (MultipartFile file : imgFiles) {
                String oriImgName = file.getOriginalFilename();
                String imgName = "";
                String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/"; // /static/files에 img 저장

                // UUID 를 이용하여 파일명 새로 생성
                // UUID - 서로 다른 객체들을 구별하기 위한 클래스
                UUID uuid = UUID.randomUUID();
                String savedFileName = uuid + "_" + oriImgName;

                imgName = savedFileName;

                File saveFile = new File(projectPath, imgName);
                try {
                    file.transferTo(saveFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ReviewImage image = new ReviewImage("/files/"+ imgName, review);
                imgPaths.add(image.getImgPath());

               // review.setReviewImage(image);
                reviewImageRepository.save(image);
            }
        }
        responseDto.setImgPaths(imgPaths);
        reviewRepository.save(review);

        updateRestaurantAvgRating(restaurant.getId());

        return responseDto;
    }

    // 리뷰 등록,수정,삭제시 사용 될 식당 평점 계산 메소드
    private void updateRestaurantAvgRating(Long restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        Double avgRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0); // 리뷰가 없으면 0.0으로 설정

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("해당 식당의 존재 하지 않습니다. = " + restaurantId));
        restaurant.setAvgRating(avgRating);// 식당 평점 update
        restaurantRepository.save(restaurant);
    }

    // 리뷰 단건 조회
    @Transactional
    public ReviewResponseDto getReview(Long reviewId) {
        ReviewResponseDto responseDto = reviewRepository.findById(reviewId)
                .map(ReviewResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID가 존재하지 않습니다."));

        List<ReviewImage> images = reviewImageRepository.findAllByReviewId(reviewId);
        List<String> paths = new ArrayList<>();
        for(ReviewImage image: images) {
            String imgPath = image.getImgPath();
            paths.add(imgPath);
        }
        responseDto.setImgPaths(paths);
        return responseDto;
    }

    // 리뷰 삭제
    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        Long restaurantId = review.getRestaurant().getId();
        reviewRepository.deleteById(reviewId); // 리뷰 삭제

        updateRestaurantAvgRating(restaurantId); // 식당 평점 update
    }

    // 리뷰 수정
    @Transactional
    public UpdateReviewResponseDto updateReview(Long reviewId, UpdateReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID가 존재하지 않습니다."));

        Long oldRestaurantId = review.getRestaurant().getId();
        review.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getRating(), requestDto.getWaitingTime(),
                requestDto.getVisitTime());

        Long newRestaurantId = requestDto.getRestaurantId();
        // 수정된 식당의 id로 변경
        if (newRestaurantId != null && !newRestaurantId.equals(oldRestaurantId)) {
            Restaurant newRestaurant = restaurantRepository.findById(newRestaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id " + newRestaurantId));
            review.setRestaurant(newRestaurant);
        }
        // 원래 식당의 평점 재계산
        updateRestaurantAvgRating(oldRestaurantId);

        // 새 식당의 평점 재계산 (식당이 변경되었을 경우)
        if (newRestaurantId != null && !newRestaurantId.equals(oldRestaurantId)) {
            updateRestaurantAvgRating(newRestaurantId);
        }
        return new UpdateReviewResponseDto(review);
    }

    public Review findById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("리뷰 id가 존재하지 않습니다."));
        return review;
    }
}