package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

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
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Handler to verify authentication of an entered account.
     * @param account an Account object provided within request body
     * @return If user is able to successfully authenticate by entering valid username/password,
     *         then API will return account with account_id as JSON and 200 status.
     *         Else if duplicate username is entered, will return 409 status.
     *         Otherwise, will return 400 status.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> postUserHandler(@RequestBody Account account){
        /**
         * Going through conditions to create new Account:
         * - username is not empty
         * - password is at least 4 characters long
         */
        if( !account.getUsername().isEmpty() &&
        account.getPassword().length() >= 4){
            // if account username exists, then 409 status code
            if(accountService.findByUsername(account.getUsername()).isPresent()){
                return ResponseEntity.status(409).build();
            } else {
                account = accountService.addAccount(account);
                return ResponseEntity.status(200).body(account);
            }
        }
        
        return ResponseEntity.status(400).build();
    }

    /**
     * Handler to verify authentication of an entered account.
     * @param account an Account object provided within request body
     * @return If user is able to successfully authenticate by entering valid username/password,
     * then API will return account with account_id as JSON and 200 status.
     * Otherwise, will return 401 status.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> postLoginHandler(@RequestBody Account account) {
        /**
         * Verifying if account exists and that entered password matches password stored
         * in database. If both true, then succcessful authentication
         */
        Optional<Account> returnedAccount = accountService.findByUsername(account.getUsername());
        if(returnedAccount.isPresent() &&
        returnedAccount.get().getPassword().equals(account.getPassword())){
            return ResponseEntity.status(200).body(returnedAccount.get());
        }

        return ResponseEntity.status(401).build();
    }
    
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessageHandler(@RequestBody Message message){
        /**
         * Going through conditions to create new Message:
         * - message_text is not blank
         * - message_text is under 255 characters
         * - posted_by refers to an existing user
         */
        if( !message.getMessageText().isEmpty() &&
        message.getMessageText().length() <= 255 &&
        accountService.findById(message.getPostedBy()).isPresent()){
            message = messageService.addMessage(message);
            return ResponseEntity.status(200).body(message);
        }

        return ResponseEntity.status(400).build();
    }
}