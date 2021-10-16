package bkdn.pbl6.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.Role;
import bkdn.pbl6.main.jwt.JwtTokenProvider;
import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.repositories.AccountRepository;
import bkdn.pbl6.main.utils.EncrytedPasswordUtils;
import io.jsonwebtoken.ExpiredJwtException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private MailService mailService;

	@Override
	public Account signup(Account account) throws Exception {
		if (accountRepository.findByEmail(account.getEmail()) != null) {
			throw new Exception("Email already exists!");
		}
		account.setPassword(EncrytedPasswordUtils.encrytedPassword(account.getPassword()));
		AccountEntity accountEntity = new AccountEntity(account);
		accountEntity.setEnable(true);

//		String token = tokenProvider.generateSignupToken(account);
//		account.setToken(token);
//		mailService.sendSignupMail(account);

		accountEntity = accountRepository.save(accountEntity);
		return new Account(accountEntity);
	}

//	@Override
//	public Account signupFinish(String token) throws Exception {
//		String email;
//		try {
//			email = tokenProvider.getEmailFromJwt(token);
//		} catch (ExpiredJwtException e) {
//			throw new Exception("Expired!");
//		}
//		AccountEntity accountEntity = accountRepository.findByEmail(email);
//		accountEntity.setValication(true);
//		accountRepository.save(accountEntity);
//		return new Account(accountEntity);
//	}
//
//	@Override
//	public Account signupRe(String email) throws Exception {
//		AccountEntity accountEntity = accountRepository.findByEmail(email);
//		if (accountEntity == null) {
//			throw new Exception("Email does not exist!");
//		}
//		if (accountEntity.getValication()) {
//			throw new Exception("Validated!");
//		}
//
//		Account account = new Account(accountEntity);
//		account.setToken(tokenProvider.generateSignupToken(account));
//		mailService.sendSignupMail(account);
//
//		return account;
//	}

}
