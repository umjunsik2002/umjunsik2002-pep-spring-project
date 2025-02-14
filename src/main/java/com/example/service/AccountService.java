package com.example.service;
import com.example.entity.*;
import com.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    public AccountService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    public Account register(String username, String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            return null;
        }
        Account newAccount = new Account(username, password);
        return accountRepository.save(newAccount);
    }
}
