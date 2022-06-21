package com.anup.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anup.blog.entities.User;
import com.anup.blog.exceptions.ResourceNotFoundException;
import com.anup.blog.payloads.UserDTO;
import com.anup.blog.repositories.UserRepository;
import com.anup.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user= this.dtoToUser(userDTO);
		User savedUser= this.userRepo.save(user);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user= this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser= this.userRepo.save(user);
		UserDTO userDTO2= this.userToDto(updatedUser);
		
		return userDTO2;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user= this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users= this.userRepo.findAll();
		
		List<UserDTO> userDTOs= users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user= this.userRepo.findById(userId)
					.orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDTO userDTO) {
		//modelMapper library removes the boilerplate code
		User user = this.modelMapper.map(userDTO, User.class);
		
		
//		below method is simple uproach to convert from dto to user  
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setAbout(userDTO.getAbout());
//		user.setPassword(userDTO.getPassword());
		return user;
	}
	
	private UserDTO userToDto(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

}
