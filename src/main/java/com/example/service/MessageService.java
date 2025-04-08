package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo){
        this.messageRepo = messageRepo;
    }
}
