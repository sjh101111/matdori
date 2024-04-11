package com.estsoft13.matdori.controller.restaurant;

import com.estsoft13.matdori.domain.*;
import com.estsoft13.matdori.dto.comment.AddCommentRequestDto;
import com.estsoft13.matdori.dto.meeting.AddMeetingRequestDto;
import com.estsoft13.matdori.dto.restaurant.AddRestaurantRequestDto;
import com.estsoft13.matdori.dto.review.AddReviewRequestDto;
import com.estsoft13.matdori.repository.*;
import com.estsoft13.matdori.service.ReviewService;
import com.estsoft13.matdori.util.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserRepository userRepository;

    private User createUser() {
        User user = new User();
        user.setRole(Role.ROLE_BEGINNER);
        user.setEmail("asd@aSd");
        user.setPassword("a");
        user.setUsername("user");
        return userRepository.save(user);
    }

    private Long reviewId;
    private Long meetingId;
    private Long userId;
    private Review review;
    private Meeting meeting;
    private User user;


    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .build();
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        //given : 저장하고 싶은 블로그 정보
        User user = createUser();

//        UserDetails userDetails = user;
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        AddRestaurantRequestDto restRequestDto = new AddRestaurantRequestDto("a","a","a",1.0);
        Restaurant restaurant = new Restaurant(restRequestDto);
        restaurant= restaurantRepository.save(restaurant);

        AddReviewRequestDto requestDto = new AddReviewRequestDto("title","content", 1.0,restaurant.getId(),1,"1");
        Review review = new Review(requestDto);

        review.setRestaurant(restaurant);
        review.setUser(user);
        reviewRepository.save(review);
        this.reviewId = review.getId();

        AddMeetingRequestDto addMeetingRequestDto = new AddMeetingRequestDto();
        addMeetingRequestDto.setContent("a");
        addMeetingRequestDto.setTitle("a");
        addMeetingRequestDto.setLocation("a");
        addMeetingRequestDto.setVisitTime("1");
        Meeting meeting = new Meeting(addMeetingRequestDto,restaurant,user);
        meetingRepository.save(meeting);
        this.meetingId = meeting.getId();

        this.userId = user.getId();

        this.review = review;
        this.meeting = meeting;
        this.user = user;
    }


    //작업중
//    @WithMockUser(username = "asd@asd", roles = {"ASSOCIATE"})
    @Test
    void addComment() throws Exception {
        //given : 저장하고 싶은 블로그 정보
        AddCommentRequestDto commentRequestDto = new AddCommentRequestDto("댓글내용입니다.");
        String json = objectMapper.writeValueAsString(commentRequestDto);

        // when : POST /api/articles
        ResultActions resultActions1 = mockMvc.perform(post("/api/comment/review/{reviewId}", reviewId).
                content(json).contentType(MediaType.APPLICATION_JSON));

        ResultActions resultActions2 = mockMvc.perform(post("/api/comment/meeting/{meetingId}", meetingId).
                content(json).contentType(MediaType.APPLICATION_JSON));

        // then : 저장이 잘 되었는지 확인, HttpStatusCode 201 검증
        resultActions1.andExpect(status().isCreated())
                .andExpect(jsonPath("content").value(commentRequestDto.getContent()))
        ;

        resultActions2.andExpect(status().isCreated())
                .andExpect(jsonPath("content").value(commentRequestDto.getContent()))

        ;
        //저장이 잘 되었는지 확인

        commentRepository.findAllByUserId(userId);
    }

    @Test
    void updateComment() throws Exception {
        //given : 데이터 저장
        Comment comment1 = new Comment();
        comment1.setContent("a");
        comment1.setReview(review);
//        comment1.setMeeting(meeting);
        comment1.setUser(user);
        //@BeforeEach 에서 Comment 생성하지 않았기 때문에 User 넣어줘야함
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("a");
//        comment.setReview(review);
        comment2.setMeeting(meeting);
        comment2.setUser(user);
        //@BeforeEach 에서 Comment 생성하지 않았기 때문에 User 넣어줘야함
        commentRepository.save(comment2);

//        Long commentId = comment1.getId();

        AddCommentRequestDto commentRequestDto = new AddCommentRequestDto("댓글내용입니다.");

        String json1 = objectMapper.writeValueAsString(commentRequestDto);
        String json2 = objectMapper.writeValueAsString(commentRequestDto);

        //when : 기능 수행 ( api 검증 )
        ResultActions resultActions1 = mockMvc.perform(put("/api/comment/review/{reviewId}/{commentId}", reviewId, comment1.getId())
                .content(json1).contentType(MediaType.APPLICATION_JSON)
        );
        ResultActions resultActions2 = mockMvc.perform(put("/api/comment/meeting/{meetingId}/{commentId}", meetingId, comment2.getId())
                .content(json2).contentType(MediaType.APPLICATION_JSON)
        );

        //then : 저장이 잘 되었는지 확인, HttpStatusCode 200
        resultActions1.andExpect(status().isOk())
                .andExpect(jsonPath("content").value(commentRequestDto.getContent()));

        resultActions2.andExpect(status().isOk())
                .andExpect(jsonPath("content").value(commentRequestDto.getContent()));
        Comment newComment1 = commentRepository.findByIdAndMeeting_Id(comment2.getId(), meetingId).orElseThrow(
                () -> new EntityNotFoundException("id is wrong")
        );
        Comment newComment2 = commentRepository.findByIdAndReview_Id(comment1.getId(), reviewId).orElseThrow(
                () -> new EntityNotFoundException("id is wrong")
        );

        assertThat(newComment1.getContent()).isEqualTo("댓글내용입니다.");
        assertThat(newComment2.getContent()).isEqualTo("댓글내용입니다.");
    }

    @Test
    void getComment() throws Exception{
        //given : 데이터 저장
        Comment comment1 = new Comment();
        comment1.setContent("a");
        comment1.setReview(review);
//        comment1.setMeeting(meeting);
        comment1.setUser(user);
        //@BeforeEach 에서 Comment 생성하지 않았기 때문에 User 넣어줘야함
         commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("a");
//        comment.setReview(review);
        comment2.setMeeting(meeting);
        comment2.setUser(user);
        //@BeforeEach 에서 Comment 생성하지 않았기 때문에 User 넣어줘야함
        commentRepository.save(comment2);

        //when : 기능 검증 ( api 검증 )
        ResultActions resultActions = mockMvc.perform(get("/api/comments/review/{reviewId}",reviewId));
        ResultActions resultActions1 = mockMvc.perform(get("/api/comments/meeting/{meetingId}", meetingId));

        //then : 저장이 잘 되었는지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(comment1.getContent()
                        ));
        resultActions1.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(comment2.getContent())
                );
    }

    @Test
    void deleteComment() throws Exception {
        //given : 데이터 저장
        Comment comment1 = new Comment();
        comment1.setContent("a");
        comment1.setReview(review);
//        comment1.setMeeting(meeting);
        comment1.setUser(user);
        //@BeforeEach 에서 Comment 생성하지 않았기 때문에 User 넣어줘야함
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("a");
//        comment.setReview(review);
        comment2.setMeeting(meeting);
        comment2.setUser(user);
        //@BeforeEach 에서 Comment 생성하지 않았기 때문에 User 넣어줘야함
        commentRepository.save(comment2);

        //when: 기능 검증
        ResultActions resultActions = mockMvc.perform(delete("/api/comment/review/{reviewId}/{commentId}", reviewId, comment1.getId()));
        ResultActions resultActions1 = mockMvc.perform(delete("/api/comment/meeting/{meetingId}/{commentId}", meetingId, comment2.getId()));

        //then: 삭제가 잘 되었는지 확인
        resultActions.andExpect(status().isOk());

        Optional<Comment> byId = commentRepository.findByIdAndReview_Id(comment1.getId(), reviewId);
        Optional<Comment> byId1 = commentRepository.findByIdAndMeeting_Id(comment2.getId(), meetingId);

        Assertions.assertFalse(byId.isPresent());
        Assertions.assertFalse(byId1.isPresent());
    }
}
