package com.venu.socialmediaplatform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Long id){
		Optional<User> userid = userRepository.findById(id);
		if(userid.isPresent()) {
			return userRepository.findById(id);
		}else {
			throw new IllegalArgumentException("User not found with that id");
		}
	}
	
	public User createUser(User user) {
		validateUser(user);
		return userRepository.save(user);
	}
	
	public User updateUser(User user) {
		Optional<User> existingUser = userRepository.findById(user.getId());
		if(existingUser.isPresent()) {
			User updatedUser = existingUser.get();
			updatedUser.setUsername(user.getUsername());
			updatedUser.setEmail(user.getEmail());
			updatedUser.setPassword(user.getPassword());
			validateUser(user);
			return userRepository.save(updatedUser);
		}
		else {
			throw new IllegalArgumentException("User not found with the given Id");
		}
	}
	
	public void deleteUserById(Long id) {
		Optional<User> existingId = userRepository.findById(id);
		if(existingId.isPresent()) {
			userRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("User not found");
		}
	}
	
	private void validateUser(User user) {
		if(user.getUsername()==null || user.getUsername().isEmpty()) {
			throw new IllegalArgumentException("Username is required");
		}
		
		if(user.getEmail()==null || user.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Email is required");
		}
		
		if(user.getPassword()==null || user.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Password is required");
		}
		
		if(user.getUsername().length()<3) {
			throw new IllegalArgumentException("Username must");
		}
		
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		if(!user.getEmail().matches(emailRegex)) {
			throw new IllegalArgumentException("Invalid email format");
		}
	}
	
}
