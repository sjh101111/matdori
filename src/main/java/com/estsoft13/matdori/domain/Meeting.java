package com.estsoft13.matdori.domain;

import com.estsoft13.matdori.dto.meeting.AddMeetingRequestDto;
import com.estsoft13.matdori.dto.meeting.MeetingResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
//@Builder
@Entity
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "location")
    private String location;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "visit_time", nullable = false)
    private String visitTime;

    public Meeting(AddMeetingRequestDto addMeetingRequestDto, Restaurant restaurant, User user) {
        this.title = addMeetingRequestDto.getTitle();
        this.content = addMeetingRequestDto.getContent();
        this.location = addMeetingRequestDto.getLocation();
        this.created_at = addMeetingRequestDto.getCreated_at();
        this.restaurant = restaurant;
        this.user = user;
        this.visitTime = addMeetingRequestDto.getVisitTime();
    }

    public void update(String title, String content, String location, String visitTime) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.visitTime = visitTime;
    }

    public MeetingResponseDto toResponse(Restaurant newRestaurant) {
        MeetingResponseDto.MeetingResponseDtoBuilder builder = MeetingResponseDto.builder()
                .content(content).visitTime(visitTime).
                title(title).location(location);

        // 만약 매개변수로 받은 레스토랑이 현재 객체의 레스토랑과 다르면, 업데이트합니다.
        if (!restaurant.equals(newRestaurant)) {
            this.restaurant = newRestaurant;
            builder.restaurant(newRestaurant); // Builder에 새 레스토랑 정보를 설정
        } else {
            builder.restaurant(this.restaurant); // 기존 레스토랑 정보를 그대로 사용
        }

        return builder.build(); // 최종적으로 DTO 객체 생성
    }

    public MeetingResponseDto toOneResponse() {
       return MeetingResponseDto.builder()
                .content(content).user_id(user.getId()).id(id).username(user.getEnteredUsername())
               .title(title).location(location).restaurant(restaurant)
               .created_at(created_at).visitTime(visitTime)
               .build();
    }
}
