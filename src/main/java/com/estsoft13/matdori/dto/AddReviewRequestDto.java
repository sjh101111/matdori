package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AddReviewRequestDto {
    private String title;
    private String content;
    private Double rating;
    private Long restaurantId;
    private int waitingTime;
    private String visitTime;
}
