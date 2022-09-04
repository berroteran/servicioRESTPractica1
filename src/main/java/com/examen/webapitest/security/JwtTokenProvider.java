package com.examen.webapitest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
	private String secretKey;
	private long expirationTime;
	
	@Autowired
	public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey, 
							@Value("${security.jwt.token.expiration}") long expirationTime) {
		this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		this.expirationTime = expirationTime;
	}
	
	public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
	
	/**
     * Get the email by the subject
     */
    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

	
}
