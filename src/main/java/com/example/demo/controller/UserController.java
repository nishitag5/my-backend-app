package com.example.demo.controller;

import com.example.demo.model.RegisteredUser;
import com.example.demo.repository.RegisteredUserRepository;
import com.example.demo.service.NotificationService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final NotificationService notificationService;

    public UserController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    private RegisteredUserRepository userRepository;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerUser(
            @RequestPart("name") String name,
            @RequestPart("email") String email,
            @RequestPart(value = "phone", required = false) String phone,
            @RequestPart("file") MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        long size = file.getSize();

        // Save to MongoDB
        RegisteredUser user = new RegisteredUser();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFileName(fileName);
        user.setFileSize(size);
        userRepository.save(user);

        // Send message to Azure SB
        String msg = String.format("User Registered: %s (%s), File: %s (%d bytes)", name, email, fileName, size);
        notificationService.sendMessage(msg);

        return ResponseEntity.ok("User registered and saved to MongoDB.");
    }


}
