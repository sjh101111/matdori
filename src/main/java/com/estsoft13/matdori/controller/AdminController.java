package com.estsoft13.matdori.controller;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.service.UserService;
import com.estsoft13.matdori.util.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage")
    public String showUsers(Model model){
        List<User> users =userService.getAllUsers();
        model.addAttribute("users", users);
        return "manage";
    }

    @PostMapping("/manage")
    public String upgradeRoles(@RequestParam Long userId, @RequestParam Role newRole){
        userService.upgradeRoles(userId, newRole);
        return "redirect:/admin/manage";
    }
}


