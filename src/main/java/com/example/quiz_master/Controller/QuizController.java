package com.example.quiz_master.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.example.quiz_master.Bean.Question;
import com.example.quiz_master.Bean.Quiz;
import com.example.quiz_master.Bean.Result;
import com.example.quiz_master.Bean.User;
import com.example.quiz_master.Service.QuestionService;
import com.example.quiz_master.Service.QuizService;
import com.example.quiz_master.Service.ResultService;
import com.example.quiz_master.Service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/Quizzes")
public class QuizController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private ResultService rs;

    @Autowired
    private UserService us;

    @GetMapping
    public String showAllQuizzes(Model model) {
        List<Quiz> quizList = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizList);
        return "Quizzes";
    }

    @GetMapping("/Quiz")
    public String showQuiz(@RequestParam(name = "quizId", required = false) Integer quizId, Model model) {
        if (quizId == null) {
            return "redirect:/Quizzes";
        }

        List<Question> questions = questionService.getByQuizId(quizId);
        Quiz quiz = quizService.getQuizById(quizId);
        model.addAttribute("questions", questions);
        model.addAttribute("quiz", quiz);
        return "Quiz"; // Must exist as quiz.html
    }

    // Handle submitted answers and show result
   @PostMapping("/submit-quiz")
public String submitQuiz(@RequestParam MultiValueMap<String, String> answers,
                         @RequestParam int quizId,
                         HttpSession session,
                         Model model) {

    User sessionUser = (User) session.getAttribute("user");
    if (sessionUser == null) {
        model.addAttribute("error", "You must be logged in to submit a quiz.");
        return "Login";
    }

    // ✅ Re-fetch the user from the DB (fixes lazy loading issue)
    User user = us.getUserById(sessionUser.getId());

    Quiz quiz = quizService.getQuizById(quizId);
    if (quiz == null) {
        model.addAttribute("error", "Quiz not found.");
        return "error";
    }

    int score = 0;
    int total = 0;

    for (String key : answers.keySet()) {
        if (!key.startsWith("q"))
            continue;

        try {
            int qId = Integer.parseInt(key.substring(1));
            String userAnswer = answers.getFirst(key);
            Question question = questionService.getQuestionById(qId);
            if (question != null && question.getCorrectAnswer().equalsIgnoreCase(userAnswer)) {
                score++;
            }
            total++;
        } catch (NumberFormatException e) {
            System.err.println("Invalid question key: " + key);
        }
    }

    Result result = new Result(user, quiz, score);
    System.out.println("Saving result: user=" + user.getUsername() + ", quiz=" + quiz.getTitle() + ", score=" + score);


    rs.save(result); // ✅ user and quiz are now properly attached entities

    model.addAttribute("score", score);
    model.addAttribute("total", total);
    return "Result";
}

}