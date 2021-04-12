package com.w2m.examen.services;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.w2m.examen.dto.LoginDTO;
import com.w2m.examen.exceptions.UserInvalidException;
import com.w2m.examen.models.User;
import com.w2m.examen.repositories.UserRepository;
import com.w2m.examen.security.JWTProvider;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTProvider jwtProvider;
	
	public String aunthenticathe(LoginDTO request) {
		User user = userRepository.findByUsernameIgnoreCaseAndPassword(request.getUsername(), request.getPassword())
				.orElseThrow(() -> new UserInvalidException("User/Password invalidos."));
		UsernamePasswordAuthenticationToken authToken = getSecurityUser(user);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		return jwtProvider.createToken(authToken);
	}

	private UsernamePasswordAuthenticationToken getSecurityUser(User user) {
		return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Arrays.asList());
	}
}
