package com.example.quiz_master.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quiz_master.Bean.Result;
import com.example.quiz_master.Bean.User;

public interface ResultRepo extends JpaRepository<Result, Integer> {
    List<Result> findAllByOrderByScoreDesc();

    Optional<Result> findTopByUserOrderByScoreDesc(User user);

    Optional<Result> findTopByUserOrderByIdDesc(User user);

    @Query("SELECT COUNT(r) FROM Result r WHERE r.user = :user")
    Long countByUser(@Param("user") User user);

}
