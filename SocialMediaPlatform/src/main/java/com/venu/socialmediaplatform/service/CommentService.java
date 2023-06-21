package com.venu.socialmediaplatform.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venu.socialmediaplatform.entities.Comment;
import com.venu.socialmediaplatform.entities.Post;
import com.venu.socialmediaplatform.entities.User;
import com.venu.socialmediaplatform.repositories.CommentRepository;
import com.venu.socialmediaplatform.repositories.PostRepository;
import com.venu.socialmediaplatform.repositories.UserRepository;

@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository, UserRepository userRepository,
			PostRepository postRepository) {
		super();
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	public List<Comment> getAllComments(){
		return commentRepository.findAll();
	}
	
	public Optional<Comment> getCommentById(Long id) {
		Optional<Comment> commentId = commentRepository.findById(id);
		if(commentId.isPresent()) {
			return commentRepository.findById(id);
		}else {
			throw new IllegalArgumentException("Comment not found with that Id");
		}
	}
	
	public Comment createComment(Comment comment, Long userId, Long postId) {
		Optional<User> existingUser = userRepository.findById(userId);
		Optional<Post> existingPost = postRepository.findById(postId);
		
		if(existingUser.isPresent() && existingPost.isPresent()) {
			comment.setTimestamp(LocalDateTime.now());
			comment.setUser(existingUser.get());
			comment.setPost(existingPost.get());
			return commentRepository.save(comment);
		}
		else {
			throw new IllegalArgumentException("Post or User not found");
		}
	}
	
	public Comment updateComment(Comment comment) {
		Optional<Comment> existingComment = commentRepository.findById(comment.getId());
		
		if(existingComment.isPresent()) {
			Comment updatedComment = existingComment.get();
			updatedComment.setContent(comment.getContent());
			updatedComment.setTimestamp(LocalDateTime.now());
			return commentRepository.save(updatedComment);
		}
		else {
			throw new IllegalArgumentException("Comment not found");
		}
	}
	
	public void deleteCommentById(Long id) {
		Optional<Comment> existingId = commentRepository.findById(id);
		if(existingId.isPresent()) {
			commentRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("Comment not found");
		}
	}
}
