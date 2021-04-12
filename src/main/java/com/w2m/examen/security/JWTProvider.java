package com.w2m.examen.security;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JWTProvider.class);
	
	@Value("${jwt.token.secret-key}")
	private String secretKey;
	
	@Value("${jwt.token.expire-length}")
	private long tokenValidityInMilliseconds;

	public String createToken(Authentication authentication) {
		final String username = String.valueOf(authentication.getPrincipal());
		final Long now = (new Date()).getTime();
		String token = getToken(username, new Date(now + tokenValidityInMilliseconds));
		return token;
	}
	
	private String getToken(String userName, Date expirationDate) {
		return Jwts.builder()
				.setSubject(userName)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.setExpiration(expirationDate).compact();
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(secretKey)
							.parseClaimsJws(token).getBody();
		return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, Arrays.asList());
	}

	public boolean validateToken(String token) {
		try {
 			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.info("Invalid JWT signature.");
			logger.trace("Invalid JWT signature trace: {}", e);
		} catch (MalformedJwtException e) {
			logger.info("Invalid JWT token.");
			logger.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			logger.info("Expired JWT token.");
			logger.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			logger.info("Unsupported JWT token.");
			logger.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			logger.info("JWT token compact of handler are invalid.");
			logger.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}
}