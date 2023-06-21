package com.venu.socialmediaplatform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.Like;
import com.venu.socialmediaplatform.entities.Post;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.LikeRepository;
import com.venu.socialmediaplatform.repositories.PostRepository;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	
	@Autowired
	public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
		super();
		this.likeRepository = likeRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	public List<Like> getAllLikes(){
		return likeRepository.findAll();
	}
	
	public Optional<Like> getLikeById(Long id) {
		Optional<Like> likeId = likeRepository.findById(id);
		if(likeId.isPresent()) {
			return likeRepository.findById(id);
		}else {
			throw new IllegalArgumentException("Like not found with that id");
		}
	}
	
	public Like createLike(Like like, Long userId, Long postId) {
		Optional<User> existingUser = userRepository.findById(userId);
		Optional<Post> existingPost = postRepository.findById(postId);
		
		if(existingUser.isPresent() && existingPost.isPresent()) {
			like.setTimestamp(LocalDateTime.now());
			like.setUser(existingUser.get());
			like.setPost(existingPost.get());
			return likeRepository.save(like);
		}else {
			throw new IllegalArgumentException("User or Post not found");
		}
		
	}
	
	public Like updateLike(Like like) {
		Optional<Like> existingLike = likeRepository.findById(like.getId());
		if(existingLike.isPresent()) {
			Like updatedLike = existingLike.get();
			updatedLike.setTimestamp(LocalDateTime.now());
			return likeRepository.save(updatedLike);
		}
		else {
			throw new IllegalArgumentException("Like not found");
		}
	}
	
	public void deleteLikeById(Long id) {
		Optional<Like> existingId = likeRepository.findById(id);
		if(existingId.isPresent()) {
			likeRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Like not found");
		}
	}
	
}
