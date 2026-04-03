package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.Message;
import com.ishimwe.digitalmarriagesystem.repository.MessageRepository;
import com.ishimwe.digitalmarriagesystem.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final SecurityUtils securityUtils;

    public MessageService(MessageRepository messageRepository, SecurityUtils securityUtils) {
        this.messageRepository = messageRepository;
        this.securityUtils = securityUtils;
    }

    public Message sendMessage(Message message) {
        message.setSenderEmail(securityUtils.getCurrentUserEmail());
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getConversation(String otherUserEmail) {
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        return messageRepository.findConversation(currentUserEmail, otherUserEmail);
    }

    public List<Message> getInbox() {
        return messageRepository.findByReceiverEmailOrderByTimestampDesc(securityUtils.getCurrentUserEmail());
    }
}
