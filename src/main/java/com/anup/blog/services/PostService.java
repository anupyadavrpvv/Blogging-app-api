package com.anup.blog.services;

import java.util.List;

import com.anup.blog.payloads.PostDTO;
import com.anup.blog.payloads.PostResponse;

public interface PostService {
	
	PostDTO createPost(PostDTO postDTO, Integer userId, Integer CategoryId);
	
	PostDTO updatePost(PostDTO postDTO, Integer postId);
	
	void deletePostById(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize);
	
	PostDTO getPostById(Integer postId);
	
	List<PostDTO> getPostByCategory(Integer categoryId);
	
	List<PostDTO> getPostByUser(Integer userId);
	
	List<PostDTO> searchPosts(String keyword);
	
}
