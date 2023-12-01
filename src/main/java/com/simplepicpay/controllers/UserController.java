package com.simplepicpay.controllers;

import com.simplepicpay.domain.user.User;
import com.simplepicpay.exception.ErrorMessage;
import com.simplepicpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            User newUser = userService.create(user);
            return ResponseEntity.ok(newUser);
        }catch (ErrorMessage message) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(message.getMessage());
        }
    }
}
