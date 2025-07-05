package com.example.quiz_master.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quiz_master.Bean.Result;
import com.example.quiz_master.Bean.User;
import com.example.quiz_master.Service.ResultService;
import com.example.quiz_master.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService us;
    @Autowired
    ResultService rs;

    @GetMapping("/")
    public String signupPage(Model model) {
        model.addAttribute("user", new User()); // ✅ use lowercase and consistent name
        return "Login";
    }

    @GetMapping("/Home")
    public String homeDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            Optional<Result> total = rs.countByUser(user);
            int high = rs.findMaxScoreByUser(user);
            Optional<Result> lastResult = rs.findLastAttemptByUser(user);

            model.addAttribute("totalQuizzes", total);
            model.addAttribute("highestScore", high);
            if (lastResult.isPresent()) {
                model.addAttribute("lastQuiz", lastResult.get().getQuiz().getTitle());
                model.addAttribute("lastScore", lastResult.get().getScore());
            } else {
                model.addAttribute("lastQuiz", "N/A");
                model.addAttribute("lastScore", 0);
            }
        }

        model.addAttribute("user", user);
        return "Home";
    }

    @GetMapping("/Register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/Register")
    public String processRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("USER");

        boolean registered = us.registerUser(user);
        if (registered) {
            model.addAttribute("success", "Registered successfully!");
            return "Login";
        } else {
            model.addAttribute("error", "User already exists!");
            return "Register";
        }
    }

    // Show Login Page
    @GetMapping("/Login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "Login";
    }

    // Login Handling
    @PostMapping("/Login")
    public String processLogin(@RequestParam String email,
            @RequestParam String password,
            Model model, HttpSession session) {

        User existingUser = us.login(email, password);

        if (existingUser != null) {
            // 👉 If email matches admin email, force role to ADMIN
            if ("arpitpatidar8827@gmail.com".equalsIgnoreCase(existingUser.getEmail())) {
                existingUser.setRole("ADMIN"); // ✅ manually set
            }

            session.setAttribute("user", existingUser);

            if ("ADMIN".equalsIgnoreCase(existingUser.getRole())) {
                return "redirect:/admin/Dashboard";
            } else {
                return "redirect:Home";
            }
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "Login";
        }
    }

    @GetMapping("/Logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Login";
    }

    @GetMapping("/Profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/Login"; // User not logged in
        }

        model.addAttribute("user", user);
        return "Profile";
    }

}
