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

import com.venu.socialmediaplatform.entities.Like;
import com.venu.socialmediaplatform.entities.Post;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.LikeService;
import com.venu.socialmediaplatform.service.PostService;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/likes")
public class LikeController {

	private final LikeService likeService;
	private final UserService userService;
	private final PostService postService;
	
	@Autowired
	public LikeController(LikeService likeService, UserService userService, PostService postService) {
		super();
		this.likeService = likeService;
		this.userService = userService;
		this.postService = postService;
	}
	
	@GetMapping
	public ResponseEntity<List<Like>> getAllLikes(){
		List<Like> like = likeService.getAllLikes();
		return ResponseEntity.ok(like);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Like> getLikeById(@PathVariable Long id){
		Optional<Like> existingLike = likeService.getLikeById(id);
		if(existingLike.isPresent()) {
			return ResponseEntity.ok(existingLike.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{userId}/{postId}")
	public ResponseEntity<Like> createLike(@RequestBody Like like, @PathVariable Long userId, @PathVariable Long postId){
		Optional<User> existingUser = userService.getUserById(userId);
		Optional<Post> existingPost = postService.getPostById(postId);
		if(existingUser.isPresent() && existingPost.isPresent()) {
			Like createdLike = likeService.createLike(like, userId, postId);
			return ResponseEntity.status(HttpStatus.OK).body(createdLike);		
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Like> updateLike(@RequestBody Like like, @PathVariable Long id){
		Optional<Like> existingLike = likeService.getLikeById(id);
		if(existingLike.isPresent()) {
			existingLike.get().setTimestamp(LocalDateTime.now());
			Like updatedLike = likeService.updateLike(existingLike.get());
			return ResponseEntity.ok(updatedLike);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLikeById(@PathVariable Long id){
		Optional<Like> existingLike = likeService.getLikeById(id);
		if(existingLike.isPresent()) {
			likeService.deleteLikeById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
