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

import com.venu.socialmediaplatform.entities.Friendship;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.FriendshipService;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

	private final FriendshipService friendshipService;
	private final UserService userService;
	
	@Autowired
	public FriendshipController(FriendshipService friendshipService, UserService userService) {
		super();
		this.friendshipService = friendshipService;
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<Friendship>> getAllFriends(){
		List<Friendship> friendship = friendshipService.getAllFriends();
		return ResponseEntity.ok(friendship);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Friendship> getFriendsById(@PathVariable Long id){
		Optional<Friendship> existingId = friendshipService.getFriendById(id);
		if(existingId.isPresent()) {
			return ResponseEntity.ok(existingId.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{userId1}/{userId2}")
	public ResponseEntity<Friendship> createFriend(@RequestBody Friendship friendship, @PathVariable Long userId1, @PathVariable Long userId2){
		Optional<User> existingUser1 = userService.getUserById(userId1);
		Optional<User> existingUser2 = userService.getUserById(userId2);
		if(existingUser1.isPresent() && existingUser2.isPresent()) {
			Friendship createdFriend = friendshipService.createFriend(friendship, userId1, userId2);
			return ResponseEntity.status(HttpStatus.OK).body(createdFriend);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Friendship> updateFriend(@RequestBody Friendship friendship, @PathVariable Long id){
		Optional<Friendship> existingId = friendshipService.getFriendById(id);
		if(existingId.isPresent()) {
			existingId.get().setTimestamp(LocalDateTime.now());
			Friendship updatedFriendship = friendshipService.updateFriend(existingId.get());
			return ResponseEntity.ok(updatedFriendship);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFriendById(@PathVariable Long id){
		Optional<Friendship> existingId = friendshipService.getFriendById(id);
		if(existingId.isPresent()) {
			friendshipService.deleteFriendshipById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
