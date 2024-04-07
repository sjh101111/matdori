package com.estsoft13.matdori.controller.restaurant;

import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.dto.comment.AddCommentRequestDto;
import com.estsoft13.matdori.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
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
    ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        commentRepository.deleteAll();
    }

    //작업중
    @WithMockUser(username = "asd@asd", roles = {"ROLE_ASSOCIATE"})
    @Test
    void addComment() throws Exception {
        //given : 저장하고 싶은 블로그 정보
        Review review = new Review();


        AddCommentRequestDto commentRequestDto = new AddCommentRequestDto("댓글내용입니다.");
        String json = objectMapper.writeValueAsString(commentRequestDto);
        Long reviewId = 1L;
        // when : POST /api/articles
        ResultActions resultActions = mockMvc.perform(post("/api/comment/review/{reviewId}", reviewId).
                content(json).contentType(MediaType.APPLICATION_JSON));

        // then : 저장이 잘 되었는지 확인, HttpStatusCode 201 검증
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("content").value(commentRequestDto.getContent()))
        ;
        //저장이 잘 되었는지 확인
    }
}
