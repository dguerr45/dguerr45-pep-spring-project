package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    /**
     * Will query Account table by given username
     * @param username a String representing the account's username
     * @return account associated with the username if it exists, otherwise null
     */
    Optional<Account> findByUsername(String username);
}