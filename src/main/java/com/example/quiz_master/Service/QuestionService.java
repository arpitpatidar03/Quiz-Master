package com.example.quiz_master.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz_master.Bean.Question;
import com.example.quiz_master.Repository.QuestionRepo;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    public Question saveQuestion(Question question) {
        return questionRepo.save(question);
    }

    public List<Question> getByQuizId(int quizId) {
        return questionRepo.findByQuizId(quizId);
    }
    public Question getQuestionById(int id) {
    return questionRepo.findById(id).orElse(null);
}

}

