package com.venu.socialmediaplatform.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="friendships")
public class Friendship {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="user_id1", referencedColumnName="id")
	private User user1;
	
	@ManyToOne
	@JoinColumn(name="user_id2", referencedColumnName="id")
	private User user2;

	public Friendship(Long id, LocalDateTime timestamp, User user1, User user2) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.user1 = user1;
		this.user2 = user2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	@Override
	public String toString() {
		return "Friendship [id=" + id + ", timestamp=" + timestamp + ", user1=" + user1 + ", user2=" + user2 + "]";
	}
	
	
}
