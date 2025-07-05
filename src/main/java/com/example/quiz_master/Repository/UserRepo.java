package com.example.quiz_master.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz_master.Bean.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email,String password);
    User findByEmail(String email);

}
