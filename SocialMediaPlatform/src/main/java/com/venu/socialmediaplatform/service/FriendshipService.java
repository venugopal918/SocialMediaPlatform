package com.venu.socialmediaplatform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.Friendship;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.FriendshipRepository;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class FriendshipService {
	 
	private final FriendshipRepository friendshipRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
		super();
		this.friendshipRepository = friendshipRepository;
		this.userRepository = userRepository;
	}
	
	public List<Friendship> getAllFriends(){
		return friendshipRepository.findAll();
	}
	
	public Optional<Friendship> getFriendById(Long id) {
		Optional<Friendship> existingFriend = friendshipRepository.findById(id);
		if(existingFriend.isPresent()) {
			return friendshipRepository.findById(id);
		}else {
			throw new IllegalArgumentException("Friend not found with that id");
		}
	}
	
	public Friendship createFriend(Friendship friendship, Long userId1, Long userId2) {
		Optional<User> existingUser1 = userRepository.findById(userId1);
		Optional<User> existingUser2 = userRepository.findById(userId2);
		
		if(existingUser1.isPresent() && existingUser2.isPresent()) {
			friendship.setTimestamp(LocalDateTime.now());
			friendship.setUser1(existingUser1.get());
			friendship.setUser2(existingUser2.get());
			return friendshipRepository.save(friendship);
		}else {
			throw new IllegalArgumentException("User is not found");
		}
	}
	
	public Friendship updateFriend(Friendship friendship) {
		Optional<Friendship> existingFriendship = friendshipRepository.findById(friendship.getId());
		if(existingFriendship.isPresent()) {
			Friendship updatedFriendship = existingFriendship.get();
			updatedFriendship.setTimestamp(LocalDateTime.now());
			return friendshipRepository.save(friendship);
		}
		else {
			throw new IllegalArgumentException("Friendship not found");
		}
	}
	
	public void deleteFriendshipById(Long id) {
		Optional<Friendship> existingId = friendshipRepository.findById(id);
		if(existingId.isPresent()) {
			friendshipRepository.deleteById(id);
		}
		else {
			throw new IllegalArgumentException("Friendship not found");
		}
	}
}
