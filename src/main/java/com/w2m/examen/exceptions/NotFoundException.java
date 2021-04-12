package com.w2m.examen.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5691540726243940539L;
	
	public NotFoundException(String message) {
		super(message);
	}
}
