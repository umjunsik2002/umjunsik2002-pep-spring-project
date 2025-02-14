package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
