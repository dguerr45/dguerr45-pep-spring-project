package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
@Transactional
public class MessageService {
    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo){
        this.messageRepo = messageRepo;
    }

    /**
     * Will create an entry in Message table. ID is not given.
     * @param message a Message object
     * @return the newly created message if successful, null otherwise
     */
    public Message addMessage(Message message){
        return this.messageRepo.save(message);
    }

    /**
     * Will return all messages from Message table
     * @return all messages as List or an empty List
     */
    @Transactional(readOnly = true)
    public List<Message> findAll(){
        return this.messageRepo.findAll();
    }

    /**
     * Will return single message from Database searching by message_id
     * @param message_id int representing message's message_id
     * @return an Optional object containing message associated with message_id or null
     *         if it doesn't exist
     */
    @Transactional(readOnly = true)
    public Optional<Message> findById(int message_id){
        return this.messageRepo.findById(Integer.valueOf(message_id));
    }

    /**
     * Will delete a message from Message table if it exists, otherwise will do nothing
     * @param message_id int representing message's message_id
     */
    public void deleteById(int message_id){
        this.messageRepo.deleteById(message_id);
    }

    /**
     * Will retrieve all messages posted by a particular user from Message table
     * @param account_id int representing user's account_id to search by
     * @return List containing all messages posted by specified user, otherwise an empty List
     *         if no messages exist
     */
    @Transactional(readOnly = true)
    public List<Message> findAllById(int account_id){
        return this.messageRepo.findAllByPostedBy(account_id);
    }
}