package bkdn.pbl6.main.services;

import java.util.Random;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

	private TreeMap<String, Integer> library = new TreeMap<>();

	private Random random = new Random(System.currentTimeMillis());

	@Override
	public String newToken(Integer id) {
		String token = "";
		for (int i = 0; i < 8; i++) {
			token += Integer.toHexString(random.nextInt());
		}
		library.put(token, id);
		System.out.println(library.toString());
		return token;
	}

	@Override
	public Integer getId(String token) {
		return library.get(token);
	}

	@Override
	public Boolean isHasId(Integer id) {
		return library.containsValue(id);
	}

}
