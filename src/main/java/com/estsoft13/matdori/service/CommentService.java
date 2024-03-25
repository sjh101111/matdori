package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.Comment;
import com.estsoft13.matdori.domain.Review;
import com.estsoft13.matdori.dto.AddCommentRequestDto;
import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.repository.CommentRepository;
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
    public Comment findById(Long id) {
       return commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("NOT FOUND ID"+id)
        );
    }

    public List<CommentResponseDto> getAllComments(Long reviewId) {
        return commentRepository.findByReview_Id(reviewId).stream()
                .map(x -> new CommentResponseDto(x)).toList();
    }

    public CommentResponseDto createCommentToReview(Long reviewId,AddCommentRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + reviewId));

        Comment newComment = new Comment(review, requestDto.getContent());
        return commentRepository.save(newComment).toResponse();
    }

    @Transactional
    public CommentResponseDto updateComment(Long reviewId, Long commentId, AddCommentRequestDto requestDto) {
        Comment comment = commentRepository.findByIdAndReview_Id(commentId, reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + reviewId));
        comment.update(requestDto.getContent());
        return comment.toResponse();
    }

    public void deleteComment(Long reviewId, Long commentId) {
        Comment comment = commentRepository.findByIdAndReview_Id(commentId, reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review not found with id" + reviewId));
        commentRepository.delete(comment);
    }
}
