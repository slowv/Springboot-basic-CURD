package com.slowv.youtuberef.common.utils;

import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {

	public static String generateJwtToken(AccountEntity account, String jwtSecret, int jwtExpiration) {
		return Jwts.builder()
				.setSubject(account.getUuid())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public static String getUserUuidFromJwtToken(String token, String jwtSecret) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public static void validateJwtToken(String token, String jwtSecret) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (Exception e) {
			throw new AuthenticationException("Invalid token");
		}
	}
}
