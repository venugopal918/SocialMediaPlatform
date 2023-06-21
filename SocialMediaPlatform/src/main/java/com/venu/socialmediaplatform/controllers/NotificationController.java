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

import com.venu.socialmediaplatform.entities.Notification;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.NotificationService;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	private final NotificationService notificationService;
	private final UserService userService;
	
	@Autowired
	public NotificationController(NotificationService notificationService, UserService userService) {
		super();
		this.notificationService = notificationService;
		this.userService = userService;
	}
	
	@GetMapping()
	public ResponseEntity<List<Notification>> getAllNotifications() {
		List<Notification> notification = notificationService.getAllNotifications();
		return ResponseEntity.ok(notification);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Notification> getNotificationById(@PathVariable Long id){
		Optional<Notification> existingNotification = notificationService.getNotificationById(id);
		if(existingNotification.isPresent()) {
			return ResponseEntity.ok(existingNotification.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<Notification> createNotification(@PathVariable Long userId, @RequestBody Notification notification){
		Optional<User> existingUser = userService.getUserById(userId);
		if(existingUser.isPresent()) {
			Notification createdNotification = notificationService.createNotification(notification, userId);
			return ResponseEntity.status(HttpStatus.OK).body(createdNotification);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
		public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification){
			Optional<Notification> existingNotification = notificationService.getNotificationById(id);
			if(existingNotification.isPresent()) {
				existingNotification.get().setMessage(notification.getMessage());
				existingNotification.get().setTimestamp(LocalDateTime.now());
				Notification updatedNotification = notificationService.updateNotification(existingNotification.get());
				return ResponseEntity.ok(updatedNotification);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteNotificationById(@PathVariable Long id){
		Optional<Notification> existingNotification = notificationService.getNotificationById(id);
		if(existingNotification.isPresent()) {
			notificationService.deleteNotificationById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
