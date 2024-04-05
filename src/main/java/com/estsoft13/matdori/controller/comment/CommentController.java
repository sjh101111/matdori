package com.estsoft13.matdori.controller.comment;

import com.estsoft13.matdori.dto.comment.AddCommentRequestDto;
import com.estsoft13.matdori.dto.comment.CommentResponseDto;
import com.estsoft13.matdori.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment CRUD", description = "댓글 관리 api")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 리뷰의 댓글 전부 조회
    @Operation(summary = "댓글 전부 표기", description = "특정 리뷰에 작성된 댓글 전부 표기")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json"))
    @Parameter(name = "id", description = "리뷰 상세페이지 ID", example = "1")
    @GetMapping("/api/comments/review/{reviewId}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsOfReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.getAllCommentsOfReview(reviewId));
    }

    // 모임의 댓글 전부 조회
    @GetMapping("/api/comments/meeting/{meetingId}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsOfMeeting(@PathVariable Long meetingId) {
        return ResponseEntity.ok(commentService.getAllCommentsOfMeeting(meetingId));
    }

    // 리뷰에 댓글 작성
    @PostMapping("/api/comment/review/{reviewId}")
    public ResponseEntity<CommentResponseDto> createCommentToReview(@PathVariable Long reviewId, @RequestBody AddCommentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createCommentToReview(reviewId, requestDto));
    }

    // 모임에 댓글 작성
    @PostMapping("/api/comment/meeting/{meetingId}")
    public ResponseEntity<CommentResponseDto> createCommentToMeeting(@PathVariable Long meetingId, @RequestBody AddCommentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createCommentToMeeting(meetingId, requestDto));
    }

    // 리뷰의 댓글 수정
    @PutMapping("/api/comment/review/{reviewId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateCommentOfReview(@PathVariable Long reviewId,
                                                                    @PathVariable Long commentId,
                                                                    @RequestBody AddCommentRequestDto requestDto) {

        return ResponseEntity.ok(commentService.updateCommentOfReview(reviewId, commentId, requestDto));
    }

    // 모임의 댓글 수정
    @PutMapping("/api/comment/meeting/{meetingId}/{commentId}")
    public ResponseEntity<CommentResponseDto> updateCommentOfMeeting(@PathVariable Long meetingId,
                                                                     @PathVariable Long commentId,
                                                                     @RequestBody AddCommentRequestDto requestDto) {

        return ResponseEntity.ok(commentService.updateCommentOfMeeting(meetingId, commentId, requestDto));
    }

    // 리뷰의 댓글 삭제
    @DeleteMapping("api/comment/review/{reviewId}/{commentId}")
    public ResponseEntity<Void> deleteCommentOfReview(@PathVariable Long reviewId,
                                                      @PathVariable Long commentId) {
        commentService.deleteCommentOfReview(reviewId, commentId);
        return ResponseEntity.ok().build();
    }

    // 모임의 댓글 삭제
    @DeleteMapping("api/comment/meeting/{meetingId}/{commentId}")
    public ResponseEntity<Void> deleteCommentOfMeeting(@PathVariable Long meetingId,
                                                       @PathVariable Long commentId) {
        commentService.deleteCommentOfMeeting(meetingId, commentId);
        return ResponseEntity.ok().build();
    }
}
