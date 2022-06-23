package com.anup.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anup.blog.entities.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer>{

}
