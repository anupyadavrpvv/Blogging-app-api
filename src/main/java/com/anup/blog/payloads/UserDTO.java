package com.anup.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.anup.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Name should be atleast of 4 characters!!")
	private String name;
	
	@Email(message = "Email address is not valid!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be >3 chars and <10 chars")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
//				message = "Password must have atleast one digit, one lower letter, one upper letter and one special character  ")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDTO> roles= new HashSet<>();
}
