package bkdn.pbl6.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.models.AccountModel;
import bkdn.pbl6.main.models.User;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	@Override
	public AccountModel login(String email, String password) {
		User user = userService.findByEmail(email);
		if (user == null)
			return null;
		System.out.println(user);
		if (!user.getPassword().equals(password))
			return null;
		if (tokenService.isHasId(user.getId()))
			return null;
		return new AccountModel(email, tokenService.newToken(user.getId()));
	}

	@Override
	public Boolean logout(String email, String token) {
		User user = userService.findByEmail(email);
		if (tokenService.getId(token) == user.getId())
			return true;
		return false;
	}

}
