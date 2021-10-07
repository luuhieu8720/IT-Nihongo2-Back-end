package bkdn.pbl6.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.models.AccountModel;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private TokenService tokenService;

	private String userToken = null;

	@Override
	public AccountModel login(String email, String password) {
		if (email.equals("user") && password.equals("user") && userToken == null) {
			userToken = tokenService.newToken();
			return new AccountModel(email, userToken);
		}
		return null;
	}

	@Override
	public Boolean logout(AccountModel model) {
		if (model.getToken().equals(userToken) && model.getEmail().equals("user")) {
			userToken = null;
			return true;
		}
		return false;
	}

}
