package bkdn.pbl6.main.repositories;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.enums.Role;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {

	AccountEntity findByEmail(String email);

	AccountEntity findByUsername(String username);

	ArrayList<AccountEntity> findByRole(Role role);

}
