package com.example.service;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    /**
     * Will create an entry in Account table. ID is not given.
     * @param account an Account object
     * @return the newly created account with auto-generated ID, otherwise null
     */
    @Transactional
    public Account addAccount(Account account){
        return this.accountRepo.save(account);
    }

    /**
     * Will query Account table by given username
     * @param username a String representing the account's username
     * @return account associated with the username if it exists, otherwise null
     */
    public Account findByUsername(String username){
        return this.accountRepo.findByUsername(username);
    }

}