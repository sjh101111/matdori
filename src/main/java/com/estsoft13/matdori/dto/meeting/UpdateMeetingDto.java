package com.estsoft13.matdori.dto.meeting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMeetingDto {
    private String title;

    private String content;

    private String location;

    private Long restaurantId;

    private String visitTime;
}
