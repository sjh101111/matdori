package com.estsoft13.matdori.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    // 접근 거부 페이지
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
