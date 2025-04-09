package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;

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
    public ResponseEntity<Account> postLoginHandler(@RequestBody Account account){
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
    
    /**
     * Handler to submit a new post to the database.
     * @param message a Message object provided within request body
     * @return If message is valid and meets criteria, then API will return message with
     *         message_id as JSON and 200 status. Otherwise, will return 400 status.
     */
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

    /**
     * Handler to gather all messages from Message table.
     * @return a List either containing all messages gathered from Message table
     *         or an empty List if none exist. Status 200 is always returned.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler(){
        List<Message> allMessages = messageService.findAll();
        return ResponseEntity.status(200).body(allMessages);
    }
    
    /**
     * Handler to retrieve a single message from Message table searching by message_id
     * @param message_id int representing message_id provided by request path
     * @return message associated with provided message_id as JSON or empty response body if
     *         no associated message is found. Status 200 is always returned.
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageHandler(@PathVariable int message_id){
        Optional<Message> returnedMessage = messageService.findById(message_id);
        if(returnedMessage.isPresent()){
            return ResponseEntity.status(200).body(returnedMessage.get());
        }
        return ResponseEntity.status(200).build();        
    }
 
    /**
     * Hnadler to delete message in Message table searching by message_id
     * @param message_id int representing message_id provided by request path
     * @return Will return 1 if message is delete, 0 otherwise. Status 200 is always
     *         returned
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageHandler(@PathVariable int message_id){
        Optional<Message> message = messageService.findById(message_id);

        if(message.isPresent()){
            messageService.deleteById(message_id);
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageHandler(@PathVariable int message_id, 
        @RequestBody Message message){
            Optional<Message> databaseMessage = messageService.findById(message_id);
            
            //if message to update doesn't exist in database
            if(databaseMessage.isEmpty()){
                return ResponseEntity.status(400).build();
            }

            /**
             * checking if new message is valid:
             * - message is not over 255 characters
             * - message is not blank
            */
            if(message.getMessageText().length() <= 255 &&
                !message.getMessageText().isEmpty()){
                    databaseMessage.get().setMessageText(message.getMessageText());
                    messageService.addMessage(databaseMessage.get());
                    return ResponseEntity.status(200).body(1);
                }
        
            return ResponseEntity.status(400).build();
    }

    /**
     * Handler to retrieve all messages posted by a particular user
     * @param account_id int representing user's account_id to search by
     * @return List containing all messages posted by specified user, otherwise an empty List
     *         if no messages exist
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserHandler(@PathVariable int account_id){
        List<Message> messagesByUser = messageService.findAllById(account_id);
        return ResponseEntity.status(200).body(messagesByUser);
    }
}