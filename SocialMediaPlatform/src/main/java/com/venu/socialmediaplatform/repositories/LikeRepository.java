package com.venu.socialmediaplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venu.socialmediaplatform.entities.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
	
}
