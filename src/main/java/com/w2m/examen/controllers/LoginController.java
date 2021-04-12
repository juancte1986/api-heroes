package com.w2m.examen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.examen.dto.LoginDTO;
import com.w2m.examen.services.LoginService;

import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@PostMapping()
	public ResponseEntity<String> login(@RequestBody LoginDTO request) {
		return status(OK).body(service.aunthenticathe(request));
	}
}