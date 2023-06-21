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
@Table(name="messages")
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50, nullable=false)
	private String content;
	
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name="sender_id", referencedColumnName="id")
	private User sender;
	
	@ManyToOne
	@JoinColumn(name="receiver_id", referencedColumnName="id")
	private User receiver;

	public Message(Long id, String content, LocalDateTime timestamp, User sender, User receiver) {
		super();
		this.id = id;
		this.content = content;
		this.timestamp = timestamp;
		this.sender = sender;
		this.receiver = receiver;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", timestamp=" + timestamp + ", sender=" + sender
				+ ", receiver=" + receiver + "]";
	}
	
	
}
