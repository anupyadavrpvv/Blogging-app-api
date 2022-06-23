package com.anup.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anup.blog.config.AppConstants;
import com.anup.blog.payloads.ApiResponse;
import com.anup.blog.payloads.PostDTO;
import com.anup.blog.payloads.PostResponse;
import com.anup.blog.services.FileService;
import com.anup.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	
	@Value("${project.image}")
	private String pathString;
	
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
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
		
		PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
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
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPosts(@PathVariable String keyword){
		List<PostDTO> result = postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		
		PostDTO postDTO = postService.getPostById(postId);
		
		String fileName= fileService.uploadImage(pathString, image);
		
		postDTO.setImageName(fileName);
		PostDTO updatePostDTO= postService.updatePost(postDTO, postId);
		
		return new ResponseEntity<PostDTO>(updatePostDTO, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resource= fileService.getResource(pathString, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
	
}
