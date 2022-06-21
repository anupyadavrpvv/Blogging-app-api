package com.anup.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anup.blog.payloads.ApiResponse;
import com.anup.blog.payloads.PostDTO;
import com.anup.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDTO> createPost(
			@RequestBody PostDTO postDTO,
			@PathVariable Integer userId,
			@PathVariable Integer catId
			){
		
		PostDTO createPost= this.postService.createPost(postDTO, userId, catId);
		
		return new ResponseEntity<PostDTO>(createPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDTO> posts= postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer catId){
		
		List<PostDTO> posts= postService.getPostByCategory(catId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<PostDTO>> getAllPosts(){
		
		List<PostDTO> posts= postService.getAllPosts();
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPost(@PathVariable Integer postId){
		
		PostDTO post= postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		
		postService.deletePostById(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post with Id : "+postId+" has been deleted successfully!!", true), HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId){
		
		PostDTO updatedPostDTO= postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatedPostDTO, HttpStatus.OK);
	}
	
}
