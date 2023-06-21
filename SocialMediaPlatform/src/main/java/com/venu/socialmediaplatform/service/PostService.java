package com.venu.socialmediaplatform.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.Post;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.PostRepository;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}
	
	public List<Post> getAllPosts(){
		return postRepository.findAll();
	}
	
	public Optional<Post> getPostById(Long id) {
		Optional<Post> postId = postRepository.findById(id);
		if(postId.isPresent()) {
			return postRepository.findById(id);
		}else {
			throw new IllegalArgumentException("Post not found with that id");
		}
	}
	
	public Post createPost(Post post, Long userId) {
		Optional<User> existingUser = userRepository.findById(userId);
		if(existingUser.isPresent()) {
			post.setTimestamp(LocalDateTime.now());
			post.setUser(existingUser.get());
			return postRepository.save(post);
		}
		else {
			throw new IllegalArgumentException("User not found with the given Id");
		}
	}
	
	public Post updatePost(Post post) {
		Optional<Post> existingPost = postRepository.findById(post.getId());
		if(existingPost.isPresent()) {
			Post updatedPost = existingPost.get();
			updatedPost.setTimestamp(LocalDateTime.now());
			updatedPost.setContent(post.getContent());
			return postRepository.save(updatedPost);
		}
		else {
			throw new IllegalArgumentException("Post not found with the given Id");
		}
	}
	
	public void deletePostById(Long id) {
		Optional<Post> existingPost = postRepository.findById(id);
		if(existingPost.isPresent()) {
			postRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Post not found");
		}
	}
	
}
