package com.example.quiz_master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.quiz_master.Bean.Result;
import com.example.quiz_master.Service.ResultService;

@Controller
public class ResultController {
    @Autowired
    private ResultService rs;

    @GetMapping("/Leaderboard")
    public String showLeaderboard(Model model) {
        List<Result> results = rs.getAllSortedByScore();
        model.addAttribute("results", results);
        return "Leaderboard";
    }
}

