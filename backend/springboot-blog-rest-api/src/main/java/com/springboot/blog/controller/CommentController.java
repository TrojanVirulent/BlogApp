package com.springboot.blog.controller;

import java.util.List;

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

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId,
													@RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("posts/{postId}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable(value="postId") long postId){
		
		return commentService.getCommentsById(postId);
	}
	
	@GetMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postId") long postId, 
													@PathVariable(value="commentId") long commentId){
		CommentDto commentDto = commentService.getCommentById(commentId, postId);
		
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}

	@PutMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value= "commentId") long commentId, 
													@PathVariable(value= "postId") long postId, 
													@RequestBody CommentDto commentDto ){
		
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);	
		
	}
	
	@DeleteMapping("posts/{postId}/comments/{commentId}")
		public ResponseEntity<String> deleteMapping(@PathVariable(value= "commentId") long commentId, 
													@PathVariable(value= "postId") long postId){
			
			commentService.deleteComment(postId, commentId);
			return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
		}
}
	
	



