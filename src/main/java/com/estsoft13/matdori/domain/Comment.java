package com.estsoft13.matdori.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
/**
 @ManyToOne
 @JoinColumn(name = "user_id")
 private User user;

 @ManyToOne
 @JoinColumn(name = "review_id)
 private Review review;

 @ManyToOne
 @JoinColumn(name = "meeting_id)
 private Meeting meeting;
 */
}

