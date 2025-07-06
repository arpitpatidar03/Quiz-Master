package com.example.quiz_master.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz_master.Bean.User;
import com.example.quiz_master.Repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo ur;

    public boolean registerUser(User user) {
        System.out.println("Trying to register: " + user.getEmail());

        User existingUser = ur.findByEmail(user.getEmail());
        if (existingUser != null) {
            System.out.println("User already exists: " + existingUser.getEmail());
            return false;
        }

        ur.save(user);
        System.out.println("User saved: " + user.getEmail());
        return true;
    }

    // User Login
    public User login(String email, String password) {
        System.out.println("Checking login for: " + email + " / " + password);
        User user = ur.findByEmailAndPassword(email, password);
        if (user != null) {
            System.out.println("User found with role: " + user.getRole());
        } else {
            System.out.println("No user found!");
        }
        return user;
    }

    // Get all users
    public List<User> getAllUsers() {
        return ur.findAll();
    }

    // Get user by ID
    public User getById(int id) {
        return ur.findById(id).orElse(null);
    }

    // Delete user by ID
    public void deleteUser(int id) {
        ur.deleteById(id);
    }
}
