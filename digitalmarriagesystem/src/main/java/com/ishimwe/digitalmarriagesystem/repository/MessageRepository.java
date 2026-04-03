package com.ishimwe.digitalmarriagesystem.repository;

import com.ishimwe.digitalmarriagesystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    @Query("SELECT m FROM Message m WHERE (m.senderEmail = :email1 AND m.receiverEmail = :email2) " +
           "OR (m.senderEmail = :email2 AND m.receiverEmail = :email1) ORDER BY m.timestamp ASC")
    List<Message> findConversation(@Param("email1") String email1, @Param("email2") String email2);

    List<Message> findByReceiverEmailOrderByTimestampDesc(String receiverEmail);
}
