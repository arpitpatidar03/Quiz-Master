package com.example.quiz_master.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz_master.Bean.Quiz;
import com.example.quiz_master.Repository.QuizRepo;

@Service
public class QuizService {
    @Autowired
    private QuizRepo quizRepo;

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    public Quiz getQuizById(int id) {
        return quizRepo.findById(id).orElse(null);
    }
    public void deleteQuiz(int id) {
    quizRepo.deleteById(id);  // qr = QuizRepository
}

}

