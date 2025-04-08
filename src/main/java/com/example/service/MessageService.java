package com.example.service;

import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
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
}
