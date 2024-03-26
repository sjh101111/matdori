package com.estsoft13.matdori.domain;

import com.estsoft13.matdori.dto.CommentResponseDto;
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

    @Builder
    public Comment(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }

    public CommentResponseDto toResponse() {
        return CommentResponseDto.builder()
                .content(content).createdAt(createdAt).build();
    }

    public Comment(Review review, String content) {
        this.review = review;
        this.content = content;
    }

    public Comment(Meeting meeting, String content) {
        this.meeting = meeting;
        this.content = content;
    }

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

     @ManyToOne
     @JoinColumn(name = "review_id")
     private Review review;

     @ManyToOne
     @JoinColumn(name = "meeting_id")
     private Meeting meeting;

    public void update(String content) {
        this.content = content;
    }

}

