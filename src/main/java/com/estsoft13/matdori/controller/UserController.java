package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.dto.UserDto;
import com.estsoft13.matdori.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

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
    @GetMapping("/signup")
    public String signup(Model model) {
            model.addAttribute("userDto", new UserDto());
            return  "signup";    // signup.html
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userDto") UserDto userDto) {
        userService.saveUser(userDto);
        return "/login";
    }

    @GetMapping("/forgot")
    public String forgot() {
        return  "forgot";    // forgot.html
    }

//    @PostMapping("/forgot")
//    public String forgot(@RequestParam String username, @RequestParam String email, Model model) {
//        String password =userService.findPasswordByUsernameAndEmail(username, email);
//        model.addAttribute("password", password);
//        return "showPassword";
//    }
}
