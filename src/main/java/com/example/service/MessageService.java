package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public int deleteMessageById(Integer messageId) {
        return messageRepository.deleteByMessageId(messageId);
    }

    public int patchMessage(Integer messageId, Message message) {
        Message found = getMessageById(messageId);
        if (!message.getMessageText().isBlank() && 
            message.getMessageText().length() <= 255 &&
            found != null) {
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }
}
