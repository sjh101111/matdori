package com.estsoft13.matdori.controller.meeting;

import com.estsoft13.matdori.dto.meeting.AddMeetingRequestDto;
import com.estsoft13.matdori.dto.meeting.MeetingResponseDto;
import com.estsoft13.matdori.dto.meeting.UpdateMeetingDto;
import com.estsoft13.matdori.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping("/api/meetings")
    public List<MeetingResponseDto> getMeetings() {
        return meetingService.getMeetings();
    }

    @GetMapping("/api/meeting/{meetingId}")
    public MeetingResponseDto getOneMeeting(@PathVariable Long meetingId) {
        return meetingService.getOneMeeting(meetingId);
    }

    @PostMapping("/api/meeting")
    public MeetingResponseDto createMeeting(@ModelAttribute AddMeetingRequestDto addMeetingRequestDto) {
        return meetingService.createMeeting(addMeetingRequestDto);
    }

    @PutMapping("/api/meeting/{meetingId}")
    public MeetingResponseDto updateMeeting(@PathVariable Long meetingId, @ModelAttribute UpdateMeetingDto updateMeetingDto) {
        return meetingService.updateMeeting(meetingId, updateMeetingDto);
    }

    @DeleteMapping("/api/meeting/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok().build();
    }

    //헤더 유저정보
}
