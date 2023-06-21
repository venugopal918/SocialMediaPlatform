package com.venu.socialmediaplatform.controllers;

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

import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		Optional<User> user = userService.getUserById(id);
		if(user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		Optional<User> existingUser = userService.getUserById(id);
		if(existingUser.isPresent()) {
			user.setId(id);
			User updatedUser = userService.updateUser(user);
			return ResponseEntity.ok(updatedUser);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
		Optional<User> existingUser = userService.getUserById(id);
		if(existingUser.isPresent()) {
			userService.deleteUserById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
