package com.estsoft13.matdori.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddMeetingRequestDto {
    private String title;

    private String content;

    private String location;

    private LocalDateTime created_at;

    private Long restaurantId;

    private String visitTime;
}
