package com.anup.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anup.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
