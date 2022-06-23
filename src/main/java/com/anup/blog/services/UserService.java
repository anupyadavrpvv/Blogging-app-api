package com.anup.blog.services;

import java.util.List;

import com.anup.blog.payloads.UserDTO;


public interface UserService {
	
	UserDTO registerNewUser(UserDTO user);
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user,Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
}
