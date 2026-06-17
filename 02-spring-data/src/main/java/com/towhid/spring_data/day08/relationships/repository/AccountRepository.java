package com.towhid.spring_data.day08.relationships.repository;

import com.towhid.spring_data.day08.relationships.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository
        extends JpaRepository<Account, Integer> {

    // Find by account number
    Optional<Account> findByAccountNumber(String accountNumber);

    // Check if account number exists
    boolean existsByAccountNumber(String accountNumber);
}