package com.venu.socialmediaplatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venu.socialmediaplatform.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
