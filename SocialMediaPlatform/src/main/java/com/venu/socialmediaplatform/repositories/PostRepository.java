package com.venu.socialmediaplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venu.socialmediaplatform.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
