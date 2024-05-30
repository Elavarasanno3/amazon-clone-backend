package com.elavarasanno3.amazonclone.controller;

import com.elavarasanno3.amazonclone.model.SignInRequest;
import com.elavarasanno3.amazonclone.model.User;
import com.elavarasanno3.amazonclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getPassword() == null || !user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        if (userService.findByEmailId(user.getEmailId()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody SignInRequest signInRequest) {
        Optional<User> optionalUser = userService.findByEmailId(signInRequest.getEmailId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(signInRequest.getPassword())) {
                return ResponseEntity.ok("Sign-in successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }
}
