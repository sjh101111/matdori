package com.estsoft13.matdori.domain;

import com.estsoft13.matdori.dto.AddReviewRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    USER 기능 완료된 후 수정 예정
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
     */

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "rest_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "rating", nullable = false)
    private Double rating;



    @Column(name = "waiting_time", nullable = false)
    private int waitingTime;

    @Column(name = "visit_time", nullable = false)
    private String visitTime;


    /*

    // 추후 예정
    @Column(name = "filename", nullable = false)
    private String filename; // 파일 이름

    @Column(name = "filepath", nullable = false)
    private String filepath; // 파일이 저장된 경로
     */

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Review(AddReviewRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.rating = requestDto.getRating();
        this.waitingTime = requestDto.getWaitingTime();
        this.visitTime = requestDto.getVisitTime();
    }

    public void update(String title, String content, Double rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }
}
