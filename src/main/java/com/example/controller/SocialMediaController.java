package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.service.*;
import com.example.entity.*;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final MessageService messageService;

    // Class constructor with Bean injection
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> postUserHandler(@RequestBody Account account){
        /**
         * Going through conditions to create new Account:
         * - username is not empty
         * - password is at least 4 characters long
         */
        if( !account.getUsername().isEmpty() &&
        account.getPassword().length() >= 4){
            // if account exists, then 409 status code
            if(accountService.getAccountByName(account.getUsername()) != null){
                return ResponseEntity.status(409).build();
            } else {
                account = accountService.addAccount(account);
                return ResponseEntity.status(200).body(account);
            }
        }
        
        return ResponseEntity.status(400).build();
    }
}