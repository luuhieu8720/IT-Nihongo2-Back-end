package bkdn.pbl6.main.configs.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.configs.models.RoleModel;
import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.repositories.AccountRepository;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		B1: Kiem tra ton tai email;
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new UsernameNotFoundException("User " + username + " was not found!");
		}

//		B2: Lay quyen;
		ArrayList<RoleModel> authorities = new ArrayList<RoleModel>();
		try {
			authorities.add(new RoleModel(accountEntity.getRole()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		AccountModel accountModel = new AccountModel(accountEntity, authorities);
		return accountModel;
	}

	public UserDetails loadUserById(String id) throws UsernameNotFoundException {
		Optional<AccountEntity> optional = accountRepository.findById(id);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("User was not found!");
		}
		AccountEntity accountEntity = optional.get();

		ArrayList<RoleModel> authorities = new ArrayList<RoleModel>();
		try {
			authorities.add(new RoleModel(accountEntity.getRole()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		AccountModel accountModel = new AccountModel(accountEntity, authorities);
		return accountModel;
	}

}
