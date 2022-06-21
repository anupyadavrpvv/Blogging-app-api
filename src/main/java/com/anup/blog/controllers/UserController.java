package com.anup.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anup.blog.payloads.UserDTO;
import com.anup.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
		UserDTO createUserDTO= this.userService.createUser(userDTO);
		return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
	}
}
