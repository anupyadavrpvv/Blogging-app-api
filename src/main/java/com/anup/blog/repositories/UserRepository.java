package com.anup.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anup.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
