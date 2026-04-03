package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.Message;
import com.ishimwe.digitalmarriagesystem.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation/{otherUserEmail}")
    public List<Message> getConversation(@PathVariable String otherUserEmail) {
        return messageService.getConversation(otherUserEmail);
    }

    @GetMapping("/inbox")
    public List<Message> getInbox() {
        return messageService.getInbox();
    }
}
