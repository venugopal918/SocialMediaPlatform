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

import com.venu.socialmediaplatform.entities.Comment;
import com.venu.socialmediaplatform.entities.Post;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.service.CommentService;
import com.venu.socialmediaplatform.service.PostService;
import com.venu.socialmediaplatform.service.UserService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private final CommentService commentService;
	private final UserService userService;
	private final PostService postService;
	
	@Autowired
	public CommentController(CommentService commentService, UserService userService, PostService postService) {
		super();
		this.commentService = commentService;
		this.userService = userService;
		this.postService = postService;
	}
	
	@GetMapping
	public ResponseEntity<List<Comment>> getAllComments(){
		List<Comment> comment = commentService.getAllComments();
		return ResponseEntity.ok(comment);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable Long id){
		Optional<Comment> existingComment = commentService.getCommentById(id);
		if(existingComment.isPresent()) {
			return ResponseEntity.ok(existingComment.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{userId}/{postId}")
	public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Long userId, @PathVariable Long postId){
		Optional<User> existingUser = userService.getUserById(userId);
		Optional<Post> existingPost = postService.getPostById(postId);
		if(existingUser.isPresent() && existingPost.isPresent()) {
			Comment createdComment = commentService.createComment(comment, userId, postId);
			return ResponseEntity.status(HttpStatus.OK).body(createdComment);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment){
		Optional<Comment> existingComment = commentService.getCommentById(id);
		if(existingComment.isPresent()) {
			existingComment.get().setContent(comment.getContent());
			existingComment.get().setTimestamp(LocalDateTime.now());
			Comment updatedComment = commentService.updateComment(existingComment.get());
			return ResponseEntity.ok(updatedComment);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCommentById(@PathVariable Long id){
		Optional<Comment> existingComment = commentService.getCommentById(id);
		if(existingComment.isPresent()) {
			commentService.deleteCommentById(id);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
