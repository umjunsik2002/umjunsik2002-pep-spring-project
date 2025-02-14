package com.example.service;
import com.example.entity.Message;
import com.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class MessageService {
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message postMessage(Message message) {
        if (!accountRepository.existsById(message.getPostedBy())) {
            return null;
        }
        return messageRepository.save(message);
    }

    public boolean deleteMessage(int messageId) {
        if (!messageRepository.existsById(messageId)) {
            return false;
        }
        messageRepository.deleteById(messageId);
        return true;
    }

    public List<Message> getMessageForAccount(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    public boolean patchMessage(Message message, int messageId) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isEmpty()) {
            return false;
        }
        existingMessage.get().setMessageText(message.getMessageText());
        messageRepository.save(existingMessage.get());
        return true;
    }
}
