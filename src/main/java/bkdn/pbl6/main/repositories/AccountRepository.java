package bkdn.pbl6.main.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.AccountEntity;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {
	
	AccountEntity findByEmail(String email);
	
	AccountEntity findByUsername(String username);

}
