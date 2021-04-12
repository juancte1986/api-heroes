package com.w2m.examen.handlers;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.w2m.examen.dto.ApiErrorDTO;
import com.w2m.examen.exceptions.NotFoundException;
import com.w2m.examen.exceptions.UserInvalidException;

@RestControllerAdvice
public class ExceptionRestHandlerRest {
	
	private final static Logger logger = LoggerFactory.getLogger(ExceptionRestHandlerRest.class);

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiErrorDTO> handleNotFoundException(final NotFoundException ex,
															final HttpServletRequest request) {
		ApiErrorDTO error = new ApiErrorDTO();
		error.setError(NOT_FOUND.getReasonPhrase());
		error.setMessage(ex.getMessage());
	    error.setStatus(NOT_FOUND.value());
	    error.setPath(request.getRequestURI());
	    error.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
	    error.setException("NotFoundException");
	    
		logger.error("handleNotFoundException", error, ex);
		return status(NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(UserInvalidException.class)
	public ResponseEntity<ApiErrorDTO> handleUserInvalidException(final UserInvalidException ex,
															final HttpServletRequest request) {
		ApiErrorDTO error = new ApiErrorDTO();
		error.setError(UNAUTHORIZED.getReasonPhrase());
		error.setMessage(ex.getMessage());
	    error.setStatus(UNAUTHORIZED.value());
	    error.setPath(request.getRequestURI());
	    error.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
	    error.setException("handleUserInvalidException");
	    
		logger.error("handleUserInvalidException", error, ex);
		return status(UNAUTHORIZED).body(error);
	}
}
