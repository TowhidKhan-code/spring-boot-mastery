package com.towhid.spring_data.day08.relationships.service;

import com.towhid.spring_data.day08.relationships.entity.Account;
import com.towhid.spring_data.day08.relationships.exception.InsufficientBalanceException;
import com.towhid.spring_data.day08.relationships.exception.TransferFailedException;
import com.towhid.spring_data.day08.relationships.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankTransferService {

    private final AccountRepository accountRepository;

    public BankTransferService(
            AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // ─────────────────────────────────────────
    // CREATE ACCOUNT
    // ─────────────────────────────────────────
    public Account createAccount(
            String accountNumber,
            String ownerName,
            Double initialBalance) {

        // Check duplicate account number
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new RuntimeException(
                    "Account already exists: " + accountNumber);
        }

        // Validate initial balance
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative");
        }

        Account account = new Account(
                accountNumber, ownerName, initialBalance);

        Account saved = accountRepository.save(account);

        System.out.println(" Account created: "
                + accountNumber
                + " | Owner: " + ownerName
                + " | Balance: $" + initialBalance);

        return saved;
    }

    // ─────────────────────────────────────────
    // GET ACCOUNT BY ID
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public Account getAccount(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found: " + id));
    }

    // ─────────────────────────────────────────
    // DEPOSIT MONEY
    // ─────────────────────────────────────────
    public Account deposit(Integer accountId, Double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be positive");
        }

        Account account = getAccount(accountId);

        account.credit(amount);

        Account saved = accountRepository.save(account);

        System.out.println(" Deposited $" + amount
                + " to account: " + account.getAccountNumber()
                + " | New balance: $" + saved.getBalance());

        return saved;
    }

    // ─────────────────────────────────────────
    // WITHDRAW MONEY
    // ─────────────────────────────────────────
    public Account withdraw(Integer accountId, Double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdraw amount must be positive");
        }

        Account account = getAccount(accountId);

        // Check sufficient balance
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    account.getBalance(), amount);
        }

        account.deduct(amount);

        Account saved = accountRepository.save(account);

        System.out.println(" Withdrew $" + amount
                + " from account: " + account.getAccountNumber()
                + " | New balance: $" + saved.getBalance());

        return saved;
    }

    // ─────────────────────────────────────────
    // TRANSFER MONEY — Main Transaction Demo!
    // ─────────────────────────────────────────
    public void transfer(
            Integer fromAccountId,
            Integer toAccountId,
            Double amount) {

        System.out.println("\n TRANSFER: $" + amount
                + " from Account " + fromAccountId
                + " to Account " + toAccountId);

        // Step 1: Validate amount
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be positive");
        }

        // Step 2: Cannot transfer to same account
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException(
                    "Cannot transfer to same account");
        }

        // Step 3: Find sender account
        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException(
                        "Sender account not found: " + fromAccountId));

        // Step 4: Find receiver account
        Account receiver = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException(
                        "Receiver account not found: " + toAccountId));

        // Step 5: Check sender has enough balance
        if (sender.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    sender.getBalance(), amount);
        }

        // Step 6: Deduct from sender
        sender.deduct(amount);
        accountRepository.save(sender);
        System.out.println("  → Deducted $" + amount
                + " from " + sender.getOwnerName());

        // Step 7: Credit to receiver
        receiver.credit(amount);
        accountRepository.save(receiver);
        System.out.println("  → Credited $" + amount
                + " to " + receiver.getOwnerName());

        System.out.println(" Transfer successful!");
        System.out.println("  " + sender.getOwnerName()
                + " balance: $" + sender.getBalance());
        System.out.println("  " + receiver.getOwnerName()
                + " balance: $" + receiver.getBalance());
    }

    // ─────────────────────────────────────────
    // TRANSFER WITH FAILURE — Rollback Demo!
    // This method INTENTIONALLY fails to show
    // that transaction rolls back!
    // ─────────────────────────────────────────
    public void transferWithFailure(
            Integer fromAccountId,
            Integer toAccountId,
            Double amount) {

        System.out.println("\n TRANSFER WITH FAILURE: $" + amount
                + " from Account " + fromAccountId
                + " to Account " + toAccountId);

        // Step 1: Validate
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transfer amount must be positive");
        }

        // Step 2: Find accounts
        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException(
                        "Sender not found: " + fromAccountId));

        Account receiver = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException(
                        "Receiver not found: " + toAccountId));

        // Step 3: Check balance
        if (sender.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    sender.getBalance(), amount);
        }

        // Step 4: Deduct from sender
        sender.deduct(amount);
        accountRepository.save(sender);
        System.out.println("  → Deducted $" + amount
                + " from " + sender.getOwnerName());

        // ═══════════════════════════════════════
        // INTENTIONAL FAILURE HERE!
        // Sender lost money but receiver
        // hasn't received it yet!
        //
        // If @Transactional works correctly:
        // → This exception triggers rollback
        // → Sender's deduction is undone
        // → Data stays consistent!
        // ═══════════════════════════════════════
        System.out.println("   SIMULATING FAILURE...");
        throw new TransferFailedException(
                "Network error during transfer!");

        // This code NEVER runs because of exception above
        // receiver.credit(amount);
        // accountRepository.save(receiver);
    }

    // ─────────────────────────────────────────
    // PRINT BALANCES — Helper for testing
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public void printBalances(Integer... accountIds) {
        System.out.println("\nCurrent Balances:");
        for (Integer id : accountIds) {
            Account acc = getAccount(id);
            System.out.println("  " + acc.getOwnerName()
                    + " (" + acc.getAccountNumber() + "): $"
                    + acc.getBalance());
        }
    }
}