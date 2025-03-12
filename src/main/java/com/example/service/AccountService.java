package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account findById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account addAccount(Account account){
        return accountRepository.save(account);
    }
}
