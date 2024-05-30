package com.elavarasanno3.amazonclone.repository;

import com.elavarasanno3.amazonclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String emailId);
}
