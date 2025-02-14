package com.example.controller;
import com.example.entity.*;
import com.example.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Integer postedBy = message.getPostedBy();
        String messageText = message.getMessageText();
        Long timePostedEpoch = message.getTimePostedEpoch();

        if (postedBy == null || postedBy < 0 ||
            messageText == null || messageText.length() == 0 || messageText.length() > 255 ||
            timePostedEpoch == null || timePostedEpoch < 0) {
            return ResponseEntity.badRequest().build();
        }
        
        Message postMessage = messageService.postMessage(message);
        if (postMessage == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postMessage);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("message_id") Integer messageId) {
        int deleteQuery = messageService.deleteMessage(messageId);
        if (deleteQuery == 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(deleteQuery);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageForAccount(@PathVariable("account_id") Integer accountId) {
        return ResponseEntity.ok(messageService.getMessageForAccount(accountId));
    }
    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessage());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> patchMessage(@RequestBody Message message, @PathVariable("message_id") Integer messageId) {
        String messageText = message.getMessageText();

        if (messageId == null || messageId < 0 ||
            messageText == null || messageText.length() == 0 || messageText.length() > 255) {
            return ResponseEntity.badRequest().build();
        }
        
        int patchQuery = messageService.patchMessage(messageId, messageText);
        if (patchQuery == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(patchQuery);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        
        if (username == null || username.length() == 0 || username.length() > 255 ||
            password == null || password.length() == 0 || password.length() > 255) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Account loginQuery = accountService.login(username, password);
        if (loginQuery == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(loginQuery);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        if (username == null || username.length() == 0 || username.length() > 255 ||
            password == null || password.length() == 0 || password.length() > 255) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Account registerQuery = accountService.register(username, password);
        if (registerQuery == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(registerQuery);
    }
}
