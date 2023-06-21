package com.venu.socialmediaplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venu.socialmediaplatform.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
