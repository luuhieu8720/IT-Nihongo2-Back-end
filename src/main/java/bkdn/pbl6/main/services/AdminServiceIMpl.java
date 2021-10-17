package bkdn.pbl6.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.repositories.AccountRepository;

@Service
public class AdminServiceIMpl implements AdminService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Boolean enable(String username, boolean enable) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}

		accountEntity.setEnable(enable);
		accountRepository.save(accountEntity);

		return true;
	}

}
