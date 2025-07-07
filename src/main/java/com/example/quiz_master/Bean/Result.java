package com.example.quiz_master.Bean;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "Result")
@Data

public class Result {

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    public Result() {} // No-arg constructor required

    public Result(User user, Quiz quiz, int score) {
        this.user = user;
        this.quiz = quiz;
        this.score = score;
    }


}


