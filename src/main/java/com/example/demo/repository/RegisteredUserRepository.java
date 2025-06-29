package com.example.demo.repository;

import com.example.demo.model.RegisteredUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisteredUserRepository extends MongoRepository<RegisteredUser, String> {
    // Optionally add query methods
}
