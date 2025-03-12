package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity postUserRegistration(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body("Client error");
        }
        if (accountService.findByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(409).body("Conflict");
        }
        Account newAccount = accountService.addAccount(account);
        return ResponseEntity.status(200).body(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity postUserLogin(@RequestBody Account account) {
        Account found = accountService.findByUsername(account.getUsername());
        if (found != null && found.getPassword().equals(account.getPassword())) {
            return ResponseEntity.status(200).body(found);
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

    @PostMapping("/messages")
    public ResponseEntity postMessage(@RequestBody Message message) {
        if (!message.getMessageText().isBlank() && 
            message.getMessageText().length() <= 255 && 
            accountService.findById(message.getPostedBy()) != null) {
            Message newMessage = messageService.addMessage(message);
            return ResponseEntity.status(200).body(newMessage);
        }
        return ResponseEntity.status(400).body("Client error");
    }

    @GetMapping("/messages")
    public ResponseEntity getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId) != 0 ? 1 : "");
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity patchMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        int rows = messageService.patchMessage(messageId, message);
        return rows != 0 ? ResponseEntity.status(200).body(rows) : ResponseEntity.status(400).body("Client error");
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getAllMessagesByAccountId(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByAccountId(accountId));
    }
}
