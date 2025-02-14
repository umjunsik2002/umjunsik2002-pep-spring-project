package com.example.service;
import com.example.entity.Message;
import com.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public int deleteMessage(int messageId) {
        return messageRepository.deleteMessage(messageId);
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

    @Transactional
    public int patchMessage(Message message, int messageId) {
        return messageRepository.updateMessage(messageId, message.getMessageText());
    }
}
