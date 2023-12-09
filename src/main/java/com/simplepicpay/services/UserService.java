package com.simplepicpay.services;

import com.simplepicpay.domain.user.User;
import com.simplepicpay.exception.ErrorMessage;
import com.simplepicpay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(User user){
        User newUser = userRepository.findUserByDocument(user.getDocument());

        if(newUser == user) {
            throw new ErrorMessage("User already exists.");
        }else {
            return userRepository.save(user);
        }
    }

    public List<User> showUsers() {
        Sort sort = Sort.by("id").ascending();

        if(sort.isEmpty()){
            throw new ErrorMessage("A lista de usuarios está vazia.");
        }
        return userRepository.findAll(sort);
    }
}
