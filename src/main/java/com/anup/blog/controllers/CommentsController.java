package com.anup.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anup.blog.payloads.ApiResponse;
import com.anup.blog.payloads.CommentsDTO;
import com.anup.blog.services.CommentsService;

@RestController
@RequestMapping("/api")
public class CommentsController {
	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentsDTO> createComment(
			@RequestBody CommentsDTO commnetsDto,
			@PathVariable Integer postId
			){
		
		CommentsDTO createComment= this.commentsService.createComment(commnetsDto, postId);
		return new ResponseEntity<CommentsDTO>(createComment, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/comments/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable Integer commentId
			){
		
		this.commentsService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment has been deleted successfully!", true), HttpStatus.OK);
		
	}
	
}
