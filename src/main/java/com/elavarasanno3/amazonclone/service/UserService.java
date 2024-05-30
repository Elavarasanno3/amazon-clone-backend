package com.elavarasanno3.amazonclone.service;

import com.elavarasanno3.amazonclone.model.User;
import com.elavarasanno3.amazonclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
