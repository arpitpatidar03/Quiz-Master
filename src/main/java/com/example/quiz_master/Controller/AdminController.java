package com.example.quiz_master.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.quiz_master.Bean.Question;
import com.example.quiz_master.Bean.Quiz;
import com.example.quiz_master.Service.QuestionService;
import com.example.quiz_master.Service.QuizService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/Dashboard")
    public String dashboard(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "admin/Dashboard";
    }

    @GetMapping("/Add-quiz")
    public String addQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "admin/Add-quiz";
    }

    @PostMapping("/Add-quiz")
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quizService.saveQuiz(quiz);
        return "redirect:/admin/Dashboard";
    }

    @GetMapping("/Add-question")
    public String addQuestionForm(@RequestParam int quizId, Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("quizId", quizId);
        return "admin/Add-question";
    }

    @PostMapping("/Add-question")
    public String saveQuestion(@ModelAttribute Question question, @RequestParam int quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        question.setQuiz(quiz);
        questionService.saveQuestion(question);
        return "redirect:/admin/Dashboard";
    }

    @PostMapping("/delete-quiz/{id}")
    public String deleteQuiz(@PathVariable("id") int id) {
        quizService.deleteQuiz(id);
        return "redirect:/admin/Dashboard";
    }

}
