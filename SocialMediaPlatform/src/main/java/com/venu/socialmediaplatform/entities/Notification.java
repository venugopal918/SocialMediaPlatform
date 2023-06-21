package com.venu.socialmediaplatform.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="notifications")
public class Notification {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=200, nullable=false)
	private String message;
	
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;

	public Notification(Long id, String message, LocalDateTime timestamp, User user) {
		super();
		this.id = id;
		this.message = message;
		this.timestamp = timestamp;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", message=" + message + ", timestamp=" + timestamp + ", user=" + user + "]";
	}
	
	
}
