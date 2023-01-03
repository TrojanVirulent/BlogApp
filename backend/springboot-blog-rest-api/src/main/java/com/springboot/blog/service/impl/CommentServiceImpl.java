package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.BlogAPIException;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repositories.CommentRepository;
import com.springboot.blog.repositories.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}
	

	@Override   
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		//map to Entity
		Comment comment = mapToEntity(commentDto);
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		//set post to comment entity
		comment.setPost(post);
		
		//comment entity to DB
		Comment newComment = commentRepository.save(comment);
		
		//map to Dto
		CommentDto commentResponse = maptoDto(newComment);
		
		return commentResponse;
	}
	
	@Override
	public List<CommentDto> getCommentsById(long postId) {
		
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		System.out.println(comments);
		
		return comments.stream().map(comment -> maptoDto(comment)).collect(Collectors.toList());
	}
	

	@Override
	public CommentDto getCommentById(long commentId, long postId) {
		 Post post = postRepository.findById(postId).orElseThrow(
				 () -> new ResourceNotFoundException("Post", "id", postId));
		 
		 Comment comment = commentRepository.findById(commentId).orElseThrow(
				 () -> new ResourceNotFoundException("Comment", "id", commentId));
		 
		 Long x1= comment.getPost().getId();
		 
		 Long x2= post.getId();
		 
		 if (!x1.equals(x2)) {
			 throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
		 }
		return maptoDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				 () -> new ResourceNotFoundException("Post", "id", postId));
		 
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				 () -> new ResourceNotFoundException("Comment", "id", commentId));
		
		Long x1= comment.getPost().getId();
		 
		Long x2= post.getId();
		 
		if (!x1.equals(x2)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
		}

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		
		return maptoDto(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		
		Post post = postRepository.findById(postId).orElseThrow(
				 () -> new ResourceNotFoundException("Post", "id", postId));
		 
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				 () -> new ResourceNotFoundException("Comment", "id", commentId));
		
		Long x1= comment.getPost().getId();
		 
		Long x2= post.getId();
		 
		if (!x1.equals(x2)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
		}
		
		commentRepository.delete(comment);
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		
		Comment comment = mapper.map(commentDto, Comment.class);
		
//		Comment comment = new Comment();
//		comment.setBody(commentDto.getBody());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
		
		return comment;
		
	}
	
	private CommentDto maptoDto(Comment comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setName(comment.getName());
//		commentDto.setBody(comment.getBody());
				
		return commentDto;
	}


	


	



	
}
