package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
@Transactional
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
    public Account addAccount(Account account){
        return this.accountRepo.save(account);
    }

    /**
     * Will query Account table by given username
     * @param username a String representing the account's username
     * @return account associated with the username if it exists, otherwise null
     */
    @Transactional(readOnly = true)
    public Optional<Account> findByUsername(String username){
        return this.accountRepo.findByUsername(username);
    }

    /**
     * Will retrieve an account from Account table searching by id.
     * @param id int representing the account's account_id
     * @return account associated with the id if it exists, otherwise null
     */
    @Transactional(readOnly = true)
    public Optional<Account> findById(int id){
        return this.accountRepo.findById(Integer.valueOf(id));
    }
}