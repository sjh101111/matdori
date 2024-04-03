package com.estsoft13.matdori.controller.user;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.user.UserDto;
import com.estsoft13.matdori.service.UserService;
import com.estsoft13.matdori.util.GeneratePassword;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";   //login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password) {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";    // signup.html
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userDto") UserDto userDto) {
        if (!userService.isEmailUnique(userDto.getEmail())) {
            return "/signup";
        } else {
            userService.saveUser(userDto);
            return "/login";
        }
    }

    @GetMapping("/forgot")
    public String forgot() {
        return "forgot";    // forgot.html
    }

    @PostMapping("/forgot")
    public String resetPassword(@RequestParam("username") String username, @RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        User user = userService.findByUsernameAndEmail(username, email);
        if (user != null) {
            String newPassword = generateRandomPassword();
            userService.resetPassword(user, newPassword);
            redirectAttributes.addFlashAttribute("msg","임시 비밀번호가 생성되었습니다. 로그인 후 변경해주세요!");
            redirectAttributes.addAttribute("newPassword", newPassword);
            return "redirect:/login";

        } else {
            redirectAttributes.addFlashAttribute("error","해당 정보가 없습니다!");
            return "/";
        }
    }

    private String generateRandomPassword() {
        return GeneratePassword.generateRandomPassword(8);
    }

}
