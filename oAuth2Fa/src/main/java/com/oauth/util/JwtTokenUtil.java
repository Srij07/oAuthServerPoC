package com.oauth.util;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.oauth.model.AuthToken;
import com.oauth.user.UserDAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserDAO userDAO;
	
	private String secret = JwtProperties.SECRET;

	public String getUsernameFromToken(String token) { return
			getClaimFromToken(token, Claims::getSubject); }

	public Date getIssuedAtDateFromToken(String token) { return
			getClaimFromToken(token, Claims::getIssuedAt); }

	public Date getExpirationDateFromToken(String token) { return
			getClaimFromToken(token, Claims::getExpiration); }

	public <T> T getClaimFromToken(String token, Function<Claims, T>
	claimsResolver) { final Claims claims = getAllClaimsFromToken(token); return
			claimsResolver.apply(claims); }

	private Claims getAllClaimsFromToken(String token) { return
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); }

	public Boolean isTokenExpired(String token) { final Date expiration =
			getExpirationDateFromToken(token); return expiration.before(new Date()); }

	private Boolean ignoreTokenExpiration(String token) { 
		// here you specify tokens, for that the expiration is ignored 
		return false; 
	}

	public String generateToken(UserDetails userDetails, String tokenType) { 
		Map<String, Object> claims = new HashMap<>();
		claims.put("Roles", userDetails.getAuthorities());
		claims.put("tokenType", tokenType);
		return doGenerateToken(claims,userDetails.getUsername(), tokenType);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject, String tokenType) {

		if(tokenType.equalsIgnoreCase("access")) {
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new
					Date(System.currentTimeMillis())) .setExpiration(new
							Date(System.currentTimeMillis() +
									JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME*1000)).signWith(SignatureAlgorithm.HS512,
											secret).compact(); 
		}
		else {
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new
					Date(System.currentTimeMillis())) .setExpiration(new
							Date(System.currentTimeMillis() +
									JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME*1000)).signWith(SignatureAlgorithm.HS512,
											secret).compact(); 

		}
	}

	public Boolean canTokenBeRefreshed(String token) { return
			(!isTokenExpired(token) || ignoreTokenExpiration(token)); }

	public Boolean validateToken(String token, UserDetails userDetails) { final
		String username = getUsernameFromToken(token); return
				(username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isAccessToken(token)); }


	public boolean isAccessToken(String token) {
		// TODO Auto-generated method stub
		Claims allClims = getAllClaimsFromToken(token);
		
		String tokenType = allClims.get("tokenType", String.class);
		if(tokenType.equalsIgnoreCase("access")) {
			return true;
		}
		else {
			return false;
		}
		
	}

	public String generateCode(UserDetails userDetails) {
		long unixTime = Instant.now().toEpochMilli();
		String usrName = userDetails.getUsername();
		String id = usrName+String.valueOf(unixTime);
		
		return id;
	}

}
