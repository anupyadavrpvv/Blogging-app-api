package com.anup.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anup.blog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
