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

import com.venu.socialmediaplatform.entities.Post;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.PostService;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	private final PostService postService;
	private final UserService userService;
	
	@Autowired
	public PostController(PostService postService, UserService userService) {
		super();
		this.postService = postService;
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts(){
		List<Post> post = postService.getAllPosts();
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable Long id){
		Optional<Post> existingPost = postService.getPostById(id);
		if(existingPost.isPresent()) {
			return ResponseEntity.ok(existingPost.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long userId){
		Optional<User> existingUser = userService.getUserById(userId);
		if(existingUser.isPresent()) {
			Post createdPost = postService.createPost(post, userId);
			return ResponseEntity.status(HttpStatus.OK).body(createdPost);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePost(@RequestBody Post post, @PathVariable Long id){
		Optional<Post> existingPost = postService.getPostById(id);
		if(existingPost.isPresent()) {
			existingPost.get().setContent(post.getContent());
			existingPost.get().setTimestamp(LocalDateTime.now());
			Post updatedPost = postService.updatePost(existingPost.get());	
			return ResponseEntity.ok(updatedPost);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePostById(@PathVariable Long id){
		Optional<Post> existingPost = postService.getPostById(id);
		if(existingPost.isPresent()) {
			postService.deletePostById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
