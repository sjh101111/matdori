package com.estsoft13.matdori.domain;

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
    private double rating;

    /* 이 부분은 생각이 필요해보임

    // 10분 단위로 FE에서 int 받는게 좋아보임
    @Column(name = "waiting_time", nullable = false)
    private int waitingTime;

    //30분 단위로
    @Column(name = "visit_time", nullable = false)
    private int visitTime;
     */

    @Column(name = "filename", nullable = false)
    private String filename; // 파일 이름

    @Column(name = "filepath", nullable = false)
    private String filepath; // 파일이 저장된 경로

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created_at;
}
