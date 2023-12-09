package com.simplepicpay.repository;

import com.simplepicpay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByDocument(String document);
    User findUserById(Long id);
}
