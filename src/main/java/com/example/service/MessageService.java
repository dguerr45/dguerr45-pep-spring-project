package com.example.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    /**
     * Will return all messages from Message table
     * @return all messages as List or an empty List
     */
    public List<Message> findAll(){
        return this.messageRepo.findAll();
    }

    /**
     * Will return single message from Database searching by message_id
     * @param message_id int representing message's message_id
     * @return message associated with message_id or null if it doesn't exist
     */
    public Message findById(int message_id){
        Optional<Message> result = this.messageRepo.findById(Integer.valueOf(message_id));
        
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
