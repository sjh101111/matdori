package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Comment;
import com.estsoft13.matdori.domain.Meeting;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.AddCommentRequestDto;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.repository.CommentRepository;
import com.estsoft13.matdori.repository.MeetingRepository;
import com.estsoft13.matdori.repository.ReviewRepository;
import com.estsoft13.matdori.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public List<CommentResponseDto> getAllCommentsOfReview(Long reviewId) {
        return commentRepository.findByReview_Id(reviewId).stream()
                .map(x -> new CommentResponseDto(
                        x.getContent(), x.getCreatedAt(), x.getUser().getEnteredUsername(), x.getId(), x.getUser().getId(), x.getUser().getRole()))
                .toList();
    }

    public List<CommentResponseDto> getAllCommentsOfMeeting(Long meetingId) {
        return commentRepository.findByMeeting_Id(meetingId).stream()
                .map(x -> new CommentResponseDto(
                        x.getContent(), x.getCreatedAt(), x.getUser().getEnteredUsername(), x.getId(), x.getUser().getId(), x.getUser().getRole())).toList();
    }

    public CommentResponseDto createCommentToReview(Long reviewId,AddCommentRequestDto requestDto) {
        User user = getAuthenticatedUser();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id " + reviewId));

        Comment newComment = new Comment(review, requestDto.getContent(), user);
        return commentRepository.save(newComment).toResponse();
    }

    public CommentResponseDto createCommentToMeeting(Long meetingId,AddCommentRequestDto requestDto) {
        User user = getAuthenticatedUser();
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + meetingId));

        Comment newComment = new Comment(meeting, requestDto.getContent(), user);
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
