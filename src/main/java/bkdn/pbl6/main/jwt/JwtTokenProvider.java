package bkdn.pbl6.main.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import bkdn.pbl6.main.configs.models.AccountModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private final String secretKey = "asdq382cvb";

	private final long expiration = 7776000000l;

	public String generateToken(AccountModel accountModel) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration);
		return Jwts.builder()
				.setSubject(accountModel.getAccount().getId())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public String getStringFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}
