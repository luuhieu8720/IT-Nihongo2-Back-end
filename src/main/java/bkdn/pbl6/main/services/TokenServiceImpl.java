package bkdn.pbl6.main.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

	@Override
	public String newToken() {
		Random random = new Random(System.currentTimeMillis());
		String token = "";
		for (int i = 0; i < 30; i++) {
			token += Integer.toHexString(random.nextInt());
		}
		return token;
	}

}
