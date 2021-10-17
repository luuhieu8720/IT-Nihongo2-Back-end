package bkdn.pbl6.main.services;

import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.models.Data;

public interface UserService {

	public Account signup(Account account) throws Exception;

	public Account signupFinish(String username, String token) throws Exception;

	public Account signupRe(String username) throws Exception;

	public Account newPassword(String username) throws Exception;

	public Data get(String username) throws Exception;

	public Data update(Data data) throws Exception;

	public Boolean updatePassword(String username, String oldPass, String newPass) throws Exception;

}
