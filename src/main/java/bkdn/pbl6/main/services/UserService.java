package bkdn.pbl6.main.services;

import bkdn.pbl6.main.models.Account;

public interface UserService {
	
	public Account signup(Account account) throws Exception;
	
//	public Account signupFinish(String token) throws Exception;
//	
//	public Account signupRe(String email) throws Exception;

}
