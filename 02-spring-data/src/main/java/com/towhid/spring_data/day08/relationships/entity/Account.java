package com.towhid.spring_data.day08.relationships.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number",
            unique = true,
            nullable = false)
    private String accountNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private Double balance;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Constructor without id and createdAt
    public Account(String accountNumber,
                   String ownerName,
                   Double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // ─────────────────────────────────────────
    // HELPER METHODS for balance operations
    // ─────────────────────────────────────────

    // Deduct money (withdraw)
    public void deduct(Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive");
        }
        if (this.balance < amount) {
            throw new RuntimeException(
                    "Insufficient balance. Available: $"
                            + this.balance
                            + ", Requested: $" + amount);
        }
        this.balance -= amount;
    }

    // Add money (deposit)
    public void credit(Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive");
        }
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + "'" +
                ", owner='" + ownerName + "'" +
                ", balance=$" + balance +
                "}";
    }
}