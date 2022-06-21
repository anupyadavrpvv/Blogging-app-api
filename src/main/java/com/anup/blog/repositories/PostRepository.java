package com.anup.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anup.blog.entities.Category;
import com.anup.blog.entities.Post;
import com.anup.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
}
