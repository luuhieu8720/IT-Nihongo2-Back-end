package bkdn.pbl6.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bkdn.pbl6.main.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

	AccountEntity findByEmail(String email);

}
