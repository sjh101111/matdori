package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.restaurant.AddRestaurantRequestDto;
import com.estsoft13.matdori.dto.review.AddReviewRequestDto;
import com.estsoft13.matdori.repository.RestaurantRepository;
import com.estsoft13.matdori.repository.ReviewImageRepository;
import com.estsoft13.matdori.repository.ReviewRepository;
import com.estsoft13.matdori.repository.UserRepository;
import com.estsoft13.matdori.util.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    private User user;
    private Restaurant restaurant;

    private User createUser() {
        User user = new User();
        user.setRole(Role.ROLE_BEGINNER);
        user.setEmail("asd@aSd");
        user.setPassword("a");
        user.setUsername("user");
        return userRepository.save(user);
    }

    private Restaurant createRestaurant() {
        AddRestaurantRequestDto restRequestDto = new AddRestaurantRequestDto("a", "a", "a", 1.0);
        Restaurant restaurant = new Restaurant(restRequestDto);
        return restaurantRepository.save(restaurant);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        this.user = createUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.restaurant = createRestaurant();
    }

    @AfterEach
    void afterSetUp() {
        reviewImageRepository.deleteAll();
        reviewRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void addReview() throws Exception {
        // MockMultipartFile를 사용하여 테스트 파일 생성
        MockMultipartFile imageFile = new MockMultipartFile(
                "imgFiles",             // 필드 이름 (Controller에서 지정한 이름과 일치해야 합니다)
                "test-image.jpg",      // 파일명
                "image/jpeg",          // 파일 타입
                "Test image content".getBytes()); // 파일의 내용

        // MockMvcRequestBuilders.multipart 사용하여 멀티파트 요청 구성
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/review");

        // Form data 추가
        builder.with(request -> {
            request.setMethod("POST");
            return request;
        });

        // 텍스트 필드 추가
        builder.param("title", "aa")
                .param("content", "aa")
                .param("waitingTime", "11")
                .param("rating", "11")
                .param("visitTime", "11")
                .param("restaurantId", String.valueOf(restaurant.getId()));

        // 파일 추가
        builder.file(imageFile);

        // MockMvc를 사용하여 요청 실행 및 검증
        ResultActions resultActions = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("aa"))
                .andExpect(jsonPath("$.content").value("aa"))
                .andExpect(jsonPath("waitingTime").value(11))
                .andDo(print());
    }

    @Test
    void updateReview() throws Exception {
        AddReviewRequestDto requestDto = new AddReviewRequestDto("title", "content", 1.0, restaurant.getId(), 1, "1");
        Review review = new Review(requestDto);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);

        MockMultipartFile imageFile = new MockMultipartFile(
                "imgFiles", "test-image", "image/jpeg", "test image content".getBytes()
        );

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/review/{reviewId}", review.getId());

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        builder.param("title", "aa")
                .param("content", "aa")
                .param("waitingTime", "11")
                .param("rating", "11")
                .param("visitTime", "11")
                .param("restaurantId", String.valueOf(restaurant.getId()));

        builder.file(imageFile);

        ResultActions resultActions = mockMvc.perform(builder)
                .andDo(print())
                .andExpect(jsonPath("title").value("aa"))
                .andExpect(jsonPath("title").value("aa"))
                .andExpect(jsonPath("waitingTime").value("11"))
                .andExpect(jsonPath("restaurantId").value(restaurant.getId()));

        Review updatedReview = reviewRepository.findById(review.getId()).orElseThrow(
                () -> new IllegalArgumentException("id is wrong " + review.getId())
        );

        assertThat(updatedReview.getContent()).isEqualTo("aa");
        assertThat(updatedReview.getTitle()).isEqualTo("aa");
        assertThat(updatedReview.getWaitingTime()).isEqualTo(11);
        assertThat(updatedReview.getContent()).isEqualTo("aa");
    }

    @Test
    void getReviews() throws Exception{
        AddReviewRequestDto requestDto = new AddReviewRequestDto("title", "content", 1.0, restaurant.getId(), 1, "1");
        Review review = new Review(requestDto);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);

        ResultActions resultActions = mockMvc.perform(get("/api/reviews"));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].title").value(review.getTitle()))
        ;
    }

    @Test
    void getEachReview() throws Exception {
        AddReviewRequestDto requestDto = new AddReviewRequestDto("title", "content", 1.0, restaurant.getId(), 1, "1");
        Review review = new Review(requestDto);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);

        ResultActions resultActions = mockMvc.perform(get("/api/review/{reviewId}", review.getId()));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("title"))
                ;
    }

    @Test
    void deleteReview() throws Exception{
        AddReviewRequestDto requestDto = new AddReviewRequestDto("title", "content", 1.0, restaurant.getId(), 1, "1");
        Review review = new Review(requestDto);
        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);

        ResultActions resultActions = mockMvc.perform(delete("/api/review/{reviewId}", review.getId()));

        resultActions.andExpect(status().isOk())
                .andDo(print());

        Optional<Review> reviewForVerify = reviewRepository.findById(review.getId());

        Assertions.assertFalse(reviewForVerify.isPresent());
    }
}
