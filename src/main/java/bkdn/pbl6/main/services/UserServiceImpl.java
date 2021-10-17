package bkdn.pbl6.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.repositories.AccountRepository;
import bkdn.pbl6.main.utils.EncryptedPasswordUtils;
import bkdn.pbl6.main.utils.TimeEncryptorUtils;

@Service
public class UserServiceImpl implements UserService {

	private final long limit = (1 << 24) - 1;
	private final long signupExpiration = 30;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MailService mailService;

	@Override
	public Account signup(Account account) throws Exception {
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			throw new Exception("Email already exists!");
		}
		if (accountRepository.findByUsername(account.getUsername()) != null) {
			throw new Exception("Username already exists!");
		}
		account.setPassword(EncryptedPasswordUtils.encryptedPassword(account.getPassword()));
		AccountEntity accountEntity = new AccountEntity(account);
		accountEntity.setEnable(false);
		accountEntity = accountRepository.save(accountEntity);

		signupRe(account.getUsername());

		return new Account(accountEntity);
	}

	@Override
	public Account signupFinish(String username, String token) throws Exception {
		long expr = TimeEncryptorUtils.decode(token.substring(0, 4));
		if (expr > (System.currentTimeMillis() / 60000)) {
			throw new Exception("Expirated!");
		}
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}
		String id = token.substring(4, 8);
		if (!accountEntity.getId().substring(0, 4).equals(id)) {
			throw new Exception("Illegal!");
		}
		if (accountEntity.getEnable()) {
			throw new Exception("Verified!");
		}
		accountEntity.setEnable(true);
		accountRepository.save(accountEntity);
		return new Account(accountEntity);
	}

	@Override
	public Account signupRe(String username) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}
		if (accountEntity.getEnable()) {
			throw new Exception("Verified!");
		}

		Account account = new Account(accountEntity);
		long expi = System.currentTimeMillis() / 60000 + signupExpiration & limit;
		String code = TimeEncryptorUtils.encode(expi) + accountEntity.getId().substring(0, 4);
		account.setToken(code);
		mailService.sendSignupMail(account);

		account.setToken("");
		return account;
	}

	@Override
	public Account newPassword(String username) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}
		long rand = System.currentTimeMillis() / 60000 + signupExpiration;
		String pass = TimeEncryptorUtils.encode(rand) + accountEntity.getId().substring(0, 4);
		accountEntity.setPassword(EncryptedPasswordUtils.encryptedPassword(pass));
		accountRepository.save(accountEntity);

		Account account = new Account(accountEntity);
		account.setPassword(pass);
		mailService.sendNewPasswordMail(account);

		account.setPassword("");
		return account;
	}

}
