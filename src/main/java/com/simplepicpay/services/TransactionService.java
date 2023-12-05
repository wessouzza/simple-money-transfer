package com.simplepicpay.services;

import com.simplepicpay.domain.transactions.Transaction;
import com.simplepicpay.domain.user.User;
import com.simplepicpay.domain.user.UserType;
import com.simplepicpay.dto.TransactionDto;
import com.simplepicpay.exception.ErrorMessage;
import com.simplepicpay.repository.TransactionRepository;
import com.simplepicpay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserType userType;

    public void createTransaction(TransactionDto transactionDto)  {
        User payer = this.userRepository.findUserById(transactionDto.payerId());
        User Payee = this.userRepository.findUserById(transactionDto.payeeId());

        validateTransaction(payer, transactionDto.amount());
    }

    public void validateTransaction (User payer, BigDecimal amount) {
        if(payer.getUserType() == UserType.MERCHANT) {
            throw new ErrorMessage("Lojistas não podem realizar transferências.");
        }

        if(payer.getBalance().compareTo(amount) < 0) {
            throw new ErrorMessage("Saldo insuficiente.");
        }
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

}