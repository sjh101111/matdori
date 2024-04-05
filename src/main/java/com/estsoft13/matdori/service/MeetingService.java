package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Meeting;
import com.estsoft13.matdori.domain.Restaurant;
import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.meeting.AddMeetingRequestDto;
import com.estsoft13.matdori.dto.meeting.MeetingResponseDto;
import com.estsoft13.matdori.dto.meeting.UpdateMeetingDto;
import com.estsoft13.matdori.repository.CommentRepository;
import com.estsoft13.matdori.repository.MeetingRepository;
import com.estsoft13.matdori.repository.RestaurantRepository;
import com.estsoft13.matdori.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final CommentRepository commentRepository;

    // 로그인된 유저 정보 찾기
    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // 모든 모임 조회 서비스
    @Transactional
    public List<MeetingResponseDto> getMeetings() {
        return meetingRepository.findAll().stream()
                .map(MeetingResponseDto::new)
                .toList();
    }

    // 특정 모임 조회 서비스
    @Transactional
    public MeetingResponseDto getOneMeeting(Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 존재하지 않습니다.")).toOneResponse();
    }

    // 모임 생성 서비스
    @Transactional
    public MeetingResponseDto createMeeting(AddMeetingRequestDto addMeetingRequestDto) {
        User user = getAuthenticatedUser();
        Restaurant restaurant = restaurantRepository.findById(addMeetingRequestDto.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("해당 식당이 존재하지 않습니다."));

        Meeting meeting = new Meeting(addMeetingRequestDto, restaurant, user);
        return new MeetingResponseDto(meetingRepository.save(meeting));
    }

    @Transactional
    public MeetingResponseDto findById(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new IllegalArgumentException("미팅 id가 존재하지 않습니다."));
        return new MeetingResponseDto(meeting);
    }

    // 모임 수정 서비스
    @Transactional
    public MeetingResponseDto updateMeeting(Long meetingId, UpdateMeetingDto updateMeetingDto) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new IllegalArgumentException("미팅 id가 존재하지 않습니다."));

        Long newRestaurantId = updateMeetingDto.getRestaurantId();
        Restaurant newRestaurant = restaurantRepository.findById(newRestaurantId).orElseThrow(() -> new IllegalArgumentException("식당정보가 존재하지 않습니다."));
        // 식당이 수정 됐다면
        meeting.update(updateMeetingDto.getTitle(), updateMeetingDto.getContent(), updateMeetingDto.getLocation(), updateMeetingDto.getVisitTime());

        return meeting.toResponse(newRestaurant);
    }

    // 모임 삭제 서비스
    @Transactional
    public void deleteMeeting(Long meetingId) {
        commentRepository.deleteByMeeting_Id(meetingId);
        meetingRepository.deleteById(meetingId);
    }

    // 모임 검색 서비스(제목, 내용, 식당이름)
    @Transactional
    public List<Meeting> searchMeeting(String keyword1, String keyword2, String keyword3) {
        return meetingRepository.findByTitleContainingOrContentContainingOrRestaurant_NameContaining(
                        keyword1, keyword2, keyword3);
    }

}

