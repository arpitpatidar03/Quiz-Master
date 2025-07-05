package com.example.quiz_master.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz_master.Bean.Question;

public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findByQuizId(int quizId);
}
