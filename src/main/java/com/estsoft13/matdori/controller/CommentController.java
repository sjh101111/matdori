package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.dto.AddCommentRequestDto;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/api/comments/{reviewId}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsOfReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getAllCommentsOfReview(reviewId));
    }

    @GetMapping("/api/comments/{meetingId}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsOfMeeting(@PathVariable Long meetingId) {
        return ResponseEntity.ok(commentService.getAllCommentsOfMeeting(meetingId));
    }

    @PostMapping("/api/comment/{reviewId}")
    public ResponseEntity<CommentResponseDto>  createCommentToReview(@PathVariable Long reviewId, @RequestBody AddCommentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createCommentToReview(reviewId, requestDto)) ;
    }

    @PostMapping("/api/comment/{meetingId}")
    public ResponseEntity<CommentResponseDto>  createCommentToMeeting(@PathVariable Long meetingId ,@RequestBody AddCommentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createCommentToMeeting(meetingId, requestDto)) ;
    }

    @PutMapping("/api/comment/{reviewId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateCommentOfReview(@PathVariable Long reviewId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody AddCommentRequestDto requestDto) {

        return ResponseEntity.ok(commentService.updateCommentOfReview(reviewId, commentId, requestDto));
    }

    @PutMapping("/api/comment/{meetingId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateCommentOfMeeting(@PathVariable Long meetingId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody AddCommentRequestDto requestDto) {

        return ResponseEntity.ok(commentService.updateCommentOfMeeting(meetingId, commentId, requestDto));
    }

    @DeleteMapping("api/comment/{reviewId}/{commentId}")
    public ResponseEntity<Void> deleteCommentOfReview(@PathVariable Long reviewId,
                                              @PathVariable Long commentId) {
        commentService.deleteCommentOfReivew(reviewId, commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/comment/{meetingId}/{commentId}")
    public ResponseEntity<Void> deleteCommentOfMeeting(@PathVariable Long meetingId,
                                              @PathVariable Long commentId) {
        commentService.deleteCommentOfMeeting(meetingId, commentId);
        return ResponseEntity.ok().build();
    }



}
