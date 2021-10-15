package bkdn.pbl6.main.services;

import bkdn.pbl6.main.models.Account;

public interface MailService {
	
	public boolean sendHello(String email);
	
	public boolean sendSignupMail(Account account);

}
