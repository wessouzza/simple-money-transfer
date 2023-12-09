package com.simplepicpay.controllers;

import com.simplepicpay.domain.user.User;
import com.simplepicpay.exception.ErrorMessage;
import com.simplepicpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> showUsers(){
        try {
            List<User> users = userService.showUsers();
            return ResponseEntity.ok(users);
        }catch (ErrorMessage message){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage());
        }
    }

}
