package com.example.quiz_master.Bean;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "quizzes")
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
