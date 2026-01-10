package com.usermanagement.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "my-super-secret-key-my-super-secret-key";

	public String generateToken(String username) {

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes())).compact();
	}
	
	public String extractUsername(String token) {
		
		return Jwts.parserBuilder().setSigningKey(SECRET.getBytes())
				.build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean isToeknValid(String token) {
		try
		{
			extractUsername(token);
			return true;
		}
		catch (JwtException e) 
		{
			return false; 	 	
		}
	}

}
