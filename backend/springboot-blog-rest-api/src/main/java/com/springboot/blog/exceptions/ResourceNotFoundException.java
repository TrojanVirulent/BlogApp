package com.springboot.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private String resourceName;
	private String fileName;
	private long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fileName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fileName, fieldValue));
		this.resourceName = resourceName;
		this.fileName = fileName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFieldValue() {
		return fieldValue;
	}


	
	

}
