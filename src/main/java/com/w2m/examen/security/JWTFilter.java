package com.w2m.examen.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JWTFilter extends GenericFilterBean {
	
	private static final String HEADER_AUT = "Authorization";
	
    private static final String BEARER_PREFIX = "Bearer ";
	
	private JWTProvider jwtProvider;

    public JWTFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
	
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final String headerAuth = httpServletRequest.getHeader(HEADER_AUT); 
		
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
			String token = headerAuth.replace(BEARER_PREFIX, "");
			if(jwtProvider.validateToken(token)) {
				UsernamePasswordAuthenticationToken userAuthenticated = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(userAuthenticated);
			}
		}
		
		chain.doFilter(request, response);
		resetAuthenticationAfterRequest();
	}
	
	private void resetAuthenticationAfterRequest() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
