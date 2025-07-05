package com.example.quiz_master.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.quiz_master.Bean.Quiz;

public interface QuizRepo extends JpaRepository<Quiz,Integer> {
    
}
