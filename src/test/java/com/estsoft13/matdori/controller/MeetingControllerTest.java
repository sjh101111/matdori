package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.Meeting;
import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.meeting.AddMeetingRequestDto;
import com.estsoft13.matdori.dto.meeting.UpdateMeetingDto;
import com.estsoft13.matdori.dto.restaurant.AddRestaurantRequestDto;
import com.estsoft13.matdori.repository.MeetingRepository;
import com.estsoft13.matdori.repository.RestaurantRepository;
import com.estsoft13.matdori.repository.UserRepository;
import com.estsoft13.matdori.util.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class MeetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MeetingRepository meetingRepository;

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
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        this.user = createUser();

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.restaurant = createRestaurant();
    }

    //ByModel과 ByJson 차이는 컨트롤러에서 data 타입을 json으로 받는지(@RequestBody),
    //URL에 포함된 쿼리구조나 application/x-www-form-urlencoded 형식의 폼 데이터나 multipart/form-data 형식의 데이터(파일 업로드 포함)(@ModelAttribute)
    @Test
    void addMeetingByModel() throws Exception {
        //given
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("content", "a");
        formData.add("title", "a");
        formData.add("location", "a");
        formData.add("visitTime", "1");
        formData.add("restaurantId", String.valueOf(restaurant.getId()));

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/meeting")
                .params(formData)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));

        //then

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("a"));

    }

    @Test
    void addMeetingByJson() throws Exception {
        AddMeetingRequestDto addMeetingRequestDto = new AddMeetingRequestDto();
        addMeetingRequestDto.setContent("a");
        addMeetingRequestDto.setTitle("a");
        addMeetingRequestDto.setLocation("a");
        addMeetingRequestDto.setVisitTime("1");
        addMeetingRequestDto.setRestaurantId(restaurant.getId());
        String json = objectMapper.writeValueAsString(addMeetingRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/meeting")
                .content(json).contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(addMeetingRequestDto.getTitle()));
    }

    //update 어노테이션 @RequestBody로 바꾸면 정상 작동
    @Test
    void updateMeeting() throws Exception {
        AddMeetingRequestDto addMeetingRequestDto = new AddMeetingRequestDto();
        addMeetingRequestDto.setContent("a");
        addMeetingRequestDto.setTitle("a");
        addMeetingRequestDto.setLocation("a");
        addMeetingRequestDto.setVisitTime("1");
        addMeetingRequestDto.setRestaurantId(restaurant.getId());
        Meeting meeting = new Meeting(addMeetingRequestDto, restaurant, user);
        meetingRepository.save(meeting);

        UpdateMeetingDto updateMeetingDto = new UpdateMeetingDto();
        updateMeetingDto.setContent("aa");
        updateMeetingDto.setTitle("aa");
        updateMeetingDto.setLocation("aa");
        updateMeetingDto.setVisitTime("11");
        updateMeetingDto.setRestaurantId(restaurant.getId());
        String json = objectMapper.writeValueAsString(updateMeetingDto);

        ResultActions resultActions = mockMvc.perform(put("/api/meeting/{meetingId}", meeting.getId())
                .content(json).contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(updateMeetingDto.getTitle()));

        assertThat(updateMeetingDto.getTitle()).isEqualTo("aa");
        assertThat(updateMeetingDto.getContent()).isEqualTo("aa");
        assertThat(updateMeetingDto.getLocation()).isEqualTo("aa");
        assertThat(updateMeetingDto.getVisitTime()).isEqualTo("11");
    }

    @Test
    void getMeeting() throws Exception {
        AddMeetingRequestDto addMeetingRequestDto = new AddMeetingRequestDto();
        addMeetingRequestDto.setContent("a");
        addMeetingRequestDto.setTitle("a");
        addMeetingRequestDto.setLocation("a");
        addMeetingRequestDto.setVisitTime("1");
//        addMeetingRequestDto.setRestaurantId(restaurant.getId());
        Meeting meeting = new Meeting(addMeetingRequestDto, restaurant, user);
        meetingRepository.save(meeting);

        AddMeetingRequestDto addMeetingRequestDto2 = new AddMeetingRequestDto();
        addMeetingRequestDto2.setContent("a");
        addMeetingRequestDto2.setTitle("a");
        addMeetingRequestDto2.setLocation("a");
        addMeetingRequestDto2.setVisitTime("1");
//        addMeetingRequestDto2.setRestaurantId(restaurant.getId());
        Meeting meeting1 = new Meeting(addMeetingRequestDto2, restaurant, user);
        meetingRepository.save(meeting1);

        ResultActions resultActions = mockMvc.perform(get("/api/meetings"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(meeting.getTitle()))
        ;
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(meeting.getContent()))
        ;
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].visitTime").value(meeting.getVisitTime()))
        ;
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value(meeting.getLocation()))
        ;
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title").value(meeting1.getTitle()))
        ;

    }

    @Test
    void deleteMeeting() throws Exception {
        AddMeetingRequestDto addMeetingRequestDto = new AddMeetingRequestDto();
        addMeetingRequestDto.setContent("a");
        addMeetingRequestDto.setTitle("a");
        addMeetingRequestDto.setLocation("a");
        addMeetingRequestDto.setVisitTime("1");
        addMeetingRequestDto.setRestaurantId(restaurant.getId());
        Meeting meeting = new Meeting(addMeetingRequestDto, restaurant, user);
        meetingRepository.save(meeting);

        ResultActions resultActions = mockMvc.perform(delete("/api/meeting/{meetingId}", meeting.getId()));

        resultActions.andExpect(status().isOk());

        Optional<Meeting> byId = meetingRepository.findById(meeting.getId());
        Assertions.assertFalse(byId.isPresent());

    }

}
