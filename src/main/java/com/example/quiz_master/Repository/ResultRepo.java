package com.example.quiz_master.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz_master.Bean.Result;
import com.example.quiz_master.Bean.User;

public interface ResultRepo extends JpaRepository<Result, Integer> {
    List<Result> findAllByOrderByScoreDesc();
    Optional<Result> findTopByUserOrderByScoreDesc(User user);
    Optional<Result> findTopByUserOrderByIdDesc(User user);
    Optional<Result> countByUser(User user);
}
