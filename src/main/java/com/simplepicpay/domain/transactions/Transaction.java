package com.simplepicpay.domain.transactions;

import com.simplepicpay.domain.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "transactions")
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;
    @ManyToOne
    @JoinColumn(name = "payee_id")
    private User payee;
    private LocalDateTime timeStamps;

    public Transaction(){}

    public Transaction(UUID id, BigDecimal amount, User payer, User payee, LocalDateTime timeStamps){
        this.id = id;
        this.amount = amount;
        this.payer = payer;
        this.payee = payee;
        this.timeStamps = timeStamps;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getPayee() {
        return payee;
    }

    public void setPayee(User payee) {
        this.payee = payee;
    }

    public LocalDateTime getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(LocalDateTime timeStamps) {
        this.timeStamps = timeStamps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(payer, that.payer) && Objects.equals(payee, that.payee) && Objects.equals(timeStamps, that.timeStamps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, payer, payee, timeStamps);
    }
}
