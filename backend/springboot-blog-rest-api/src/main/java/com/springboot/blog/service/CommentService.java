package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsById(long postId);
	
	CommentDto getCommentById(long commentId, long postId);
	
	CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);
	
	void deleteComment(long postId, long commentId);
}
