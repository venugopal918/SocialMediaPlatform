package com.venu.socialmediaplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venu.socialmediaplatform.entities.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

}
