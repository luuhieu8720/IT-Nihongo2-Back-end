package bkdn.pbl6.main.services;

import bkdn.pbl6.main.models.AccountModel;

public interface AuthenticationService {

	public AccountModel login(String email, String password);
	
	public Boolean logout(String email, String token);

}
