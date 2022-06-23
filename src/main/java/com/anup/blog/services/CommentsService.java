package com.anup.blog.services;

import com.anup.blog.payloads.CommentsDTO;

public interface CommentsService {
	
	
	CommentsDTO createComment(CommentsDTO commentsDTO, Integer postId);
	
	void deleteComment(Integer commentId);
	
}
