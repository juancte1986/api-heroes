package com.w2m.examen.exceptions;

public class UserInvalidException extends RuntimeException {

	private static final long serialVersionUID = -3531516508963234402L;
	
	public UserInvalidException(String message) {
		super(message);
	}
}
