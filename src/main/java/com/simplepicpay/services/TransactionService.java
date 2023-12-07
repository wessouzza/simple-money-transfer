package com.simplepicpay.services;

import com.simplepicpay.domain.transactions.Transaction;
import com.simplepicpay.domain.user.User;
import com.simplepicpay.domain.user.UserType;
import com.simplepicpay.dto.TransactionDto;
import com.simplepicpay.exception.ErrorMessage;
import com.simplepicpay.repository.TransactionRepository;
import com.simplepicpay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDto transactionDto)  {
        User payer = this.userRepository.findUserById(transactionDto.payerId());
        User payee = this.userRepository.findUserById(transactionDto.payeeId());

        validateTransaction(payer, transactionDto.amount());

        if(!this.authTransaction(payer, transactionDto.amount())){
            throw new ErrorMessage("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDto.amount());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);


        payer.setBalance(payer.getBalance().subtract(transactionDto.amount()));
        payee.setBalance(payee.getBalance().add(transactionDto.amount()));

        this.transactionRepository.save(newTransaction);
        this.saveUser(payer);
        this.saveUser(payee);
    }

    public void validateTransaction (User payer, BigDecimal amount) {
        if(payer.getUserType() == UserType.MERCHANT) {
            throw new ErrorMessage("Lojistas não podem realizar transferências.");
        }

        if(payer.getBalance().compareTo(amount) < 0) {
            throw new ErrorMessage("Saldo insuficiente.");
        }
    }

    public boolean authTransaction(User payer, BigDecimal amount) {
        ResponseEntity<Map> authResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if(authResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else {
            return false;
        }
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

}