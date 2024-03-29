package com.estsoft13.matdori.dto;

import com.estsoft13.matdori.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequestDto {
    private String content;
//    private LocalDateTime createdAt;

    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }
}

