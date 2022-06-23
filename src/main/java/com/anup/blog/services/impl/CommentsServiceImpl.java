package com.anup.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anup.blog.entities.Comments;
import com.anup.blog.entities.Post;
import com.anup.blog.exceptions.ResourceNotFoundException;
import com.anup.blog.payloads.CommentsDTO;
import com.anup.blog.repositories.CommentsRepository;
import com.anup.blog.repositories.PostRepository;
import com.anup.blog.services.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService{
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentsRepository commentsRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentsDTO createComment(CommentsDTO commentsDTO, Integer postId) {

		Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		
		Comments comments= modelMapper.map(commentsDTO, Comments.class);
		
		comments.setPost(post);
		Comments savedComments= this.commentsRepository.save(comments);
		
		return modelMapper.map(savedComments, CommentsDTO.class);
		
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comments comments= this.commentsRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comments", "Comments id", commentId));
		this.commentsRepository.delete(comments);
	}

}
