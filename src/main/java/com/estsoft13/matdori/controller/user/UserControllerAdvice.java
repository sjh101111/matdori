package com.estsoft13.matdori.controller.user;

import com.estsoft13.matdori.dto.user.UserDto;
import com.estsoft13.matdori.repository.UserRepository;
import com.estsoft13.matdori.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice
public class UserControllerAdvice {

    private final UserService userService; // 사용자 서비스

    private final UserRepository userRepository;

    @ModelAttribute("currentUser")
    private UserDto getAuthenticatedUser() {
        // 현재 사용자가 인증되었는지 여부를 체크합니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return new UserDto(); // 인증되지 않은 사용자의 경우 null을 반환합니다.
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return new UserDto(userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username))) ;
        }
    }
}
