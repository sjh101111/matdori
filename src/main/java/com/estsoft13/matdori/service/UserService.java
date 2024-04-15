package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Meeting;
import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.user.UserDto;
import com.estsoft13.matdori.repository.*;
import com.estsoft13.matdori.util.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final ReviewRepository reviewRepository;
    private final MeetingRepository meetingRepository;
    private final CommentRepository commentRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewImageRepository reviewImageRepository;

    // 유저 저장
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_BEGINNER);

        userRepository.save(user);
    }

    // 관리자 저장
    public void saveAdmin(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_ADMIN);

        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    public User findByUsernameAndEmail(String email, String username) {
        return userRepository.findByUsernameAndEmail(email, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found by: " + email + " and " + username));
    }


    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Transactional
    // 비밀번호 변경
    public void resetPassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 유저 업그레이드
    public void upgradeRoles(Long userId, Role newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        user.setRole(newRole);

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        List<Review> reviews = reviewRepository.findAllByUserId(userId);
        for (Review review : reviews) {
            Long reviewId = review.getId();
            Long restaurantId = review.getRestaurant().getId();

            commentRepository.deleteByReview_Id(reviewId); // 리뷰 삭제 전 연결된 댓글 삭제
            reviewImageRepository.deleteByReview(review); //리뷰 삭제전 연결된 리뷰 이미지 삭제
            reviewRepository.deleteById(reviewId); // 리뷰 삭제

            updateRestaurantAvgRating(restaurantId); // 식당 평점 update
        }

        List<Meeting> meetings = meetingRepository.findAllByUserId(userId);
        for (Meeting meeting : meetings) {
            Long meetingId = meeting.getId();
            commentRepository.deleteByMeeting_Id(meetingId); // 모임 삭제 전 연결된 댓글 삭제
            meetingRepository.deleteById(meetingId); // 모임 삭제
        }
        commentRepository.deleteByUserId(userId); // 사용자가 작성한 모든 댓글 삭제
        userRepository.deleteById(userId);
    }

    private void updateRestaurantAvgRating(Long restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        Double avgRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0); // 리뷰가 없으면 0.0으로 설정

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("해당 식당의 존재 하지 않습니다. = " + restaurantId));
        // 식당 평점 update
        restaurant.setAvgRating(avgRating);

        restaurantRepository.save(restaurant);
    }

    public User findById(Long userId) {
         return userRepository.findById(userId).orElseThrow(
                 () -> new IllegalArgumentException("Invalid user Id:" + userId));
    }

    public boolean checkPassword(User user, String password) {
        return encoder.matches(password, user.getPassword());
    }
}
