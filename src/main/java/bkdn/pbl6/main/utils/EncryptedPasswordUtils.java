package bkdn.pbl6.main.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String encode(String password) {
		return encoder.encode(password);
	}

	public static Boolean matches(String raw, String encoded) {
		return encoder.matches(raw, encoded);
	}

}
