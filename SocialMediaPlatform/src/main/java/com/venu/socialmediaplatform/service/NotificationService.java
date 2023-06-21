package com.venu.socialmediaplatform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.Notification;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.NotificationRepository;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
		super();
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}
	
	public List<Notification> getAllNotifications(){
		return notificationRepository.findAll();
	}
	
	public Optional<Notification> getNotificationById(Long id){
		Optional<Notification> existingId = notificationRepository.findById(id);
		if(existingId.isPresent()) {
			return notificationRepository.findById(id);
		}else {
			throw new IllegalArgumentException("Notification not found with that id");
		}
	}
	
	public Notification createNotification(Notification notification, Long userId) {
		Optional<User> existingUser = userRepository.findById(userId);
		if(existingUser.isPresent()) {
			notification.setTimestamp(LocalDateTime.now());
			notification.setUser(existingUser.get());
			return notificationRepository.save(notification);
		}else {
			throw new IllegalArgumentException("User not found with that id");
		}
	}
	
	public Notification updateNotification(Notification notification) {
		Optional<Notification> existingId = notificationRepository.findById(notification.getId());
		if(existingId.isPresent()) {
			Notification updatedNotification = existingId.get();
			updatedNotification.setTimestamp(LocalDateTime.now());
			updatedNotification.setMessage(notification.getMessage());
			return notificationRepository.save(updatedNotification);
		}else {
			throw new IllegalArgumentException("Notification not found with that id");
		}
	}
	
	public void deleteNotificationById(Long id) {
		Optional<Notification> existingId = notificationRepository.findById(id);
		if(existingId.isPresent()) {
			notificationRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Notification not found with that id");
		}
	}
}
