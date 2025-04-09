package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    /**
     * Will retrieve all messages posted by a particular user
     * @param account_id int representing user's account_id to search by
     * @return List containing all messages posted by specified user, otherwise an empty List
     *         if no messages exist
     */
    List<Message> findAllByPostedBy(int account_id);
}
