package com.anup.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.anup.blog.entities.Category;
import com.anup.blog.entities.Post;
import com.anup.blog.entities.User;
import com.anup.blog.exceptions.ResourceNotFoundException;
import com.anup.blog.payloads.PostDTO;
import com.anup.blog.payloads.PostResponse;
import com.anup.blog.repositories.CategoryRepository;
import com.anup.blog.repositories.PostRepository;
import com.anup.blog.repositories.UserRepository;
import com.anup.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer catId) {
		
		User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		
		Category category= this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", catId));
		
		Post post= this.modelMapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost= this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post= postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		
		Post updatedPost= postRepo.save(post);
		
		return modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePostById(Integer postId) {

		Post post= postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize) {
		
		Pageable pageable= (Pageable) PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost= this.postRepo.findAll(pageable);
		
		List<Post> posts= pagePost.getContent();
		List<PostDTO> postDtos= posts.stream().map((post)->modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse ;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		
		Post post= postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		return modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		
		Category category= this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		List<Post> posts= this.postRepo.findByCategory(category);
		
		List<PostDTO> postDTOs= posts.stream()
										.map((post)-> this.modelMapper.map(post, PostDTO.class))
										.collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {

		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		
		List<Post> posts= this.postRepo.findByUser(user);
		
		List<PostDTO> postDTOs= posts.stream()
										.map((post)-> this.modelMapper.map(post, PostDTO.class))
										.collect(Collectors.toList());
		
		return postDTOs;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
