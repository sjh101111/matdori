package com.estsoft13.matdori.dto.comment;

import com.estsoft13.matdori.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class UpdateCommentDto {
    private String content;
    private LocalDateTime createdAt;

    public Comment toEntity() {
        return Comment.builder().content(content).createdAt(createdAt).build();
    }
}
