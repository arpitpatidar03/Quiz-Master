package com.example.quiz_master.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz_master.Bean.Result;
import com.example.quiz_master.Bean.User;
import com.example.quiz_master.Repository.ResultRepo;

@Service
public class ResultService {
    @Autowired
    private ResultRepo repo;

    public void save(Result r) {
        repo.save(r);
    }

    public List<Result> getAllSortedByScore() {
        return repo.findAllByOrderByScoreDesc();
    }

    public Long countByUser(User user) {
        return repo.countByUser(user);
    }

    public int findMaxScoreByUser(User user) {
        return repo.findTopByUserOrderByScoreDesc(user)
                .map(Result::getScore)
                .orElse(0);
    }

    public Optional<Result> findLastAttemptByUser(User user) {
        return repo.findTopByUserOrderByIdDesc(user);
    }

}
