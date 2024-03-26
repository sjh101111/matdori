package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Comment;
import com.estsoft13.matdori.domain.Meeting;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.dto.AddCommentRequestDto;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.repository.CommentRepository;
import com.estsoft13.matdori.repository.MeetingRepository;
import com.estsoft13.matdori.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final MeetingRepository meetingRepository;

    public List<CommentResponseDto> getAllCommentsOfReview(Long reviewId) {
        return commentRepository.findByReview_Id(reviewId).stream()
                .map(x -> new CommentResponseDto(x)).toList();
    }

    public List<CommentResponseDto> getAllCommentsOfMeeting(Long meetingId) {
        return commentRepository.findByMeeting_Id(meetingId).stream()
                .map(x -> new CommentResponseDto(x)).toList();
    }

    public CommentResponseDto createCommentToReview(Long reviewId,AddCommentRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + reviewId));

        Comment newComment = new Comment(review, requestDto.getContent());
        return commentRepository.save(newComment).toResponse();
    }

    public CommentResponseDto createCommentToMeeting(Long meetingId,AddCommentRequestDto requestDto) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + meetingId));

        Comment newComment = new Comment(meeting, requestDto.getContent());
        return commentRepository.save(newComment).toResponse();
    }

    @Transactional
    public CommentResponseDto updateCommentOfReview(Long reviewId, Long commentId, AddCommentRequestDto requestDto) {
        Comment comment = commentRepository.findByIdAndReview_Id(commentId, reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + reviewId));
        comment.update(requestDto.getContent());
        return comment.toResponse();
    }

    @Transactional
    public CommentResponseDto updateCommentOfMeeting(Long meetingId, Long commentId, AddCommentRequestDto requestDto) {
        Comment comment = commentRepository.findByIdAndMeeting_Id(commentId, meetingId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + meetingId));
        comment.update(requestDto.getContent());
        return comment.toResponse();
    }

    public void deleteCommentOfReivew(Long reviewId, Long commentId) {
        Comment comment = commentRepository.findByIdAndReview_Id(commentId, reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + reviewId));
        commentRepository.delete(comment);
    }

    public void deleteCommentOfMeeting(Long meetingId, Long commentId) {
        Comment comment = commentRepository.findByIdAndMeeting_Id(commentId, meetingId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + meetingId));
        commentRepository.delete(comment);
    }
}
