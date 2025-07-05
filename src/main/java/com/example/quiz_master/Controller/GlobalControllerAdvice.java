package com.example.quiz_master.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.quiz_master.Bean.User;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("user")
    public User globalUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}

