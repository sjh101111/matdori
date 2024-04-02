package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Setter
@Getter
public class CommentResponseDto {
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private Long id;
    private Long userId;
    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

    @Builder
    public CommentResponseDto(String content, LocalDateTime createdAt,String username, Long id, Long userId) {
        this.content = content;
        this.createdAt =createdAt;
        this.username = username;
        this.id = id;
        this.userId = userId;
    }

}

