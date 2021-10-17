package bkdn.pbl6.main.services;

import bkdn.pbl6.main.models.Account;

public interface UserService {

	public Account signup(Account account) throws Exception;

	public Account signupFinish(String username, String token) throws Exception;

	public Account signupRe(String username) throws Exception;

	public Account newPassword(String username) throws Exception;

}
