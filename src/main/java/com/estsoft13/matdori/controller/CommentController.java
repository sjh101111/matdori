package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.dto.AddCommentRequestDto;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/api/comments/{reviewId}")
    public List<CommentResponseDto> getAllComments(@PathVariable Long reviewId) {
        return commentService.getAllComments(reviewId);
    }

    @PostMapping("/api/comment/{reviewId}")
    public CommentResponseDto createCommentToReview(@PathVariable Long reviewId ,@RequestBody AddCommentRequestDto requestDto) {
        return commentService.createCommentToReview(reviewId, requestDto);
    }

    @PutMapping("/api/comment/{reviewId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long reviewId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody AddCommentRequestDto requestDto) {

        return ResponseEntity.ok(commentService.updateComment(reviewId, commentId, requestDto));
    }

    @DeleteMapping("api/comment/{reviewId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long reviewId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(reviewId, commentId);
        return ResponseEntity.ok().build();
    }

}
