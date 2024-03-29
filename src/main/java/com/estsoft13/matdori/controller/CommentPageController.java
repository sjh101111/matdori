package com.estsoft13.matdori.controller;


import com.estsoft13.matdori.dto.CommentResponseDto;
import com.estsoft13.matdori.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentPageController {
    private final CommentService commentService;

    @GetMapping("/comments/{reviewId}")
    public String getAllCommentsOfReview(@PathVariable Long reviewId, Model model) {
        List<CommentResponseDto> responseDtos = commentService.getAllCommentsOfReview(reviewId);
        model.addAttribute("comments", responseDtos);
        return "comments";
    }
}
