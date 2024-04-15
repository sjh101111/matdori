package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.User;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    void addUser() throws Exception {
//        User user = new User();
//        user.setUsername("aa");
//        user.setEmail("aa");
//        user.setPassword("a");
//        user.setRole(Role.ROLE_BEGINNER);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "a");
        formData.add("email", "a");
        formData.add("password", "a");
        formData.add("role", String.valueOf(Role.ROLE_BEGINNER));

        ResultActions resultActions = mockMvc.perform(post("/signup")
                .params(formData).contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
        ;
        User user = userRepository.findByEmail("a").orElseThrow(
                () -> new IllegalArgumentException("email is wrong")
        );

        assertThat(user.getEmail()).isEqualTo("a");
    }

    @Test
    void login() throws Exception {
        User user = new User();
        user.setUsername("aa");
        user.setEmail("aa@a");
        user.setPassword(encoder.encode("a"));
        user.setRole(Role.ROLE_BEGINNER);
        userRepository.save(user);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", "aa@a");
        formData.add("password", "a");
        //로그인 시도시 전달하는 비밀번호는 인코딩된 후 해시값으로 저장된 비밀번호와 비교를 하기 때문에 인코딩되지 않은 a값이랑 비교하면 틀린다
        ResultActions resultActions = mockMvc.perform(post("/login")
                        .param("email", "aa@a")
                        .param("password", "a")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reviews"));
    }

    @Test
    void lostPassword() throws Exception {
        User user = new User();
        user.setUsername("aa");
        user.setEmail("aa@a");
        user.setPassword(encoder.encode("a"));
        user.setRole(Role.ROLE_BEGINNER);
        userRepository.save(user);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", "aa");
        formData.add("email","aa@a");

        ResultActions resultActions = mockMvc.perform(post("/forgot")
                .params(formData).contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("id is wrong")
        );

        Assertions.assertFalse(user.getPassword().equals(user1.getPassword()));
    }

    @Test
    void changePassword() throws Exception{
        User user = new User();
        user.setUsername("aa");
        user.setEmail("aa@a");
        user.setPassword(encoder.encode("a"));
        user.setRole(Role.ROLE_BEGINNER);
        userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("password", "a");
        formData.add("newPassword", "aa");

        ResultActions resultActions = mockMvc.perform(post("/changePassword")
//                        .with(SecurityMockMvcRequestPostProcessors.user("aa"))
                        .params(formData).contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andDo(print());

        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("id is wrong")
        );

//        assertThat(user1.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertFalse(user1.getPassword().equals(user.getPassword()));
    }

    @Test
    void deleteUser() throws Exception{
        User user = new User();
        user.setUsername("aa");
        user.setEmail("aa@a");
        user.setPassword(encoder.encode("a"));
        user.setRole(Role.ROLE_BEGINNER);
        userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        ResultActions resultActions = mockMvc.perform(post("/remove")
                .param("password", "a")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));

        resultActions.andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/login"));

        Optional<User> verifyUser = userRepository.findByEmail(user.getEmail());

        Assertions.assertFalse(verifyUser.isPresent());
    }

}
