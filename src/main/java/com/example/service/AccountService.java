package com.example.service;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    @Transactional
    public Account addAccount(Account account){
        return this.accountRepo.save(account);
    }

    public Account getAccountByName(String username){
        return this.accountRepo.getAccountByName(username);
    }

}