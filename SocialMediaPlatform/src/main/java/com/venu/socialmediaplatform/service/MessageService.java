package com.venu.socialmediaplatform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.Message;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.MessageRepository;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
		super();
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
	}
	
	public List<Message> getAllMessages(){
		return messageRepository.findAll();
	}
	
	public Optional<Message> getMessageById(Long Id){
		Optional<Message> existingId = messageRepository.findById(Id);
		if(existingId.isPresent()) {
			return messageRepository.findById(Id);
		}else {
			throw new IllegalArgumentException("Message not found with that Id");
		}
	}
	
	public Message createMessage(Message message, Long senderId, Long receiverId) {
		Optional<User> sender = userRepository.findById(senderId);
		Optional<User> receiver = userRepository.findById(receiverId);
		
		if(sender.isPresent() && receiver.isPresent()) {
			message.setTimestamp(LocalDateTime.now());
			message.setSender(sender.get());
			message.setReceiver(receiver.get());
			return messageRepository.save(message);
		}else {
			throw new IllegalArgumentException("Sender or Receiver not found");
		}
	}
	
	public Message updateMessage(Message message) {
		Optional<Message> existingId = messageRepository.findById(message.getId());
		if(existingId.isPresent()) {
			Message updatedMessage = existingId.get();
			updatedMessage.setContent(message.getContent());
			updatedMessage.setTimestamp(LocalDateTime.now());
			return messageRepository.save(updatedMessage);
		}else {
			throw new IllegalArgumentException("Message not found");
		}
	}
	
	public void deleteMessageById(Long id) {
		Optional<Message> existingId = messageRepository.findById(id);
		if(existingId.isPresent()) {
			messageRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Message not found");
		}
	}
}
