package com.venu.socialmediaplatform.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venu.socialmediaplatform.entities.Message;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.MessageService;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/messages")
public class MessageController {
	
	private final MessageService messageService;
	private final UserService userService;
	
	@Autowired
	public MessageController(MessageService messageService, UserService userService) {
		super();
		this.messageService = messageService;
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<Message>> getAllMessages() {
		List<Message> message = messageService.getAllMessages();
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Message> getMessageById(@PathVariable Long id){
		Optional<Message> existingMessage = messageService.getMessageById(id);
		if(existingMessage.isPresent()) {
			return ResponseEntity.ok(existingMessage.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{senderId}/{receiverId}")
	public ResponseEntity<Message> createMessage(@RequestBody Message message, @PathVariable Long senderId, @PathVariable Long receiverId){
		Optional<User> existingSenderId = userService.getUserById(senderId);
		Optional<User> existingReceiverId = userService.getUserById(receiverId);
		if(existingSenderId.isPresent() && existingReceiverId.isPresent()) {
			Message createdMessage = messageService.createMessage(message, senderId, receiverId);
			return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Message> updateMessage(@RequestBody Message message, @PathVariable Long id){
		Optional<Message> existingMessage = messageService.getMessageById(id);
		if(existingMessage.isPresent()) {
			existingMessage.get().setContent(message.getContent());
			existingMessage.get().setTimestamp(LocalDateTime.now());
			Message updatedMessage = messageService.updateMessage(existingMessage.get());
			return ResponseEntity.ok(updatedMessage);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMessageById(@PathVariable Long id){
		Optional<Message> existingMessage = messageService.getMessageById(id);
		if(existingMessage.isPresent()) {
			messageService.deleteMessageById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
