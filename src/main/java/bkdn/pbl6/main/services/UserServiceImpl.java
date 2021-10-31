package bkdn.pbl6.main.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.DataEntity;
import bkdn.pbl6.main.enums.Gender;
import bkdn.pbl6.main.enums.Role;
import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.models.Data;
import bkdn.pbl6.main.repositories.AccountRepository;
import bkdn.pbl6.main.repositories.DataRepository;
import bkdn.pbl6.main.utils.EncryptedPasswordUtils;
import bkdn.pbl6.main.utils.TimeEncryptorUtils;

@Service
public class UserServiceImpl implements UserService {

	private final long limit = (1 << 24) - 1;
	private final long signupExpiration = 30;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private DataRepository dataRepository;

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
		account.setPassword(EncryptedPasswordUtils.encode(account.getPassword()));
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

		accountEntity.setPassword(EncryptedPasswordUtils.encode(pass));
		accountRepository.save(accountEntity);

		Account account = new Account(accountEntity);
		account.setPassword(pass);
		mailService.sendNewPasswordMail(account);

		account.setPassword("");
		return account;
	}

	@Override
	public Data get(String username) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}
		DataEntity dataEntity;
		if (accountEntity.getIdData() == null) {
			dataEntity = new DataEntity();
			dataEntity.setIdAccount(accountEntity.getId());
			dataRepository.save(dataEntity);
			accountEntity.setIdData(dataEntity.getId());
			accountEntity = accountRepository.save(accountEntity);
		} else {
			Optional<DataEntity> optional = dataRepository.findById(accountEntity.getIdData());
			if (optional.isEmpty()) {
				Exception e = new Exception("Data not found!");
				e.printStackTrace();
				throw e;
			}
			dataEntity = optional.get();
		}
		return new Data(accountEntity, dataEntity);
	}

	@Override
	public Data update(Data data) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(data.getUsername());
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}
		DataEntity dataEntity;
		if (accountEntity.getIdData() == null) {
			dataEntity = new DataEntity(data);
			dataEntity.setIdAccount(accountEntity.getId());
			dataRepository.save(dataEntity);
			accountEntity.setIdData(dataEntity.getId());
			accountEntity = accountRepository.save(accountEntity);
		} else {
			Optional<DataEntity> optional = dataRepository.findById(accountEntity.getIdData());
			if (optional.isEmpty()) {
				Exception e = new Exception("Data not found!");
				e.printStackTrace();
				throw e;
			}
			dataEntity = optional.get();
		}

		if (data.getName() != null) {
			accountEntity.setName(data.getName());
			accountEntity = accountRepository.save(accountEntity);
		}

		if (data.getGender() != null)
			dataEntity.setGender(data.getGender());
		if (data.getAddress() != null)
			dataEntity.setAddress(data.getAddress());
		if (data.getAvatar() != null)
			dataEntity.setAvatar(data.getAvatar());
		if (data.getTelephone() != null)
			dataEntity.setTelephone(data.getTelephone());

		if (accountEntity.getRole() == Role.Tutor) {
			if (data.getDegree() != null)
				dataEntity.setDegree(data.getDegree());
			if (data.getSpecialty() != null)
				dataEntity.setSpecialty(data.getSpecialty());
			if (data.getStudentId() != null)
				dataEntity.setStudentId(data.getStudentId());
		}
		dataEntity = dataRepository.save(dataEntity);

		accountEntity.setEmail("");
		return new Data(accountEntity, dataEntity);
	}

	@Override
	public Boolean updatePassword(String username, String oldPass, String newPass) throws Exception {
		AccountEntity accountEntity = accountRepository.findByUsername(username);
		if (accountEntity == null) {
			throw new Exception("Username does not exist!");
		}
		if (!EncryptedPasswordUtils.matches(oldPass, accountEntity.getPassword())) {
			throw new Exception("Password incorrect!");
		}

		accountEntity.setPassword(EncryptedPasswordUtils.encode(newPass));
		accountRepository.save(accountEntity);

		return true;
	}

	@Override
	public ArrayList<Data> getAll(Data data) throws Exception {

		ArrayList<AccountEntity> accountEntities = accountRepository.findByRole(data.getRole());

		ArrayList<Data> datas = new ArrayList<>();
		for (AccountEntity accountEntity : accountEntities) {
			DataEntity dataEntity;
			if (accountEntity.getIdData() == null) {

				dataEntity = new DataEntity(data);
				dataEntity.setIdAccount(accountEntity.getId());
				dataRepository.save(dataEntity);

				accountEntity.setIdData(dataEntity.getId());
				accountEntity = accountRepository.save(accountEntity);

			} else {
				Optional<DataEntity> optional = dataRepository.findById(accountEntity.getIdData());

				if (optional.isEmpty()) {
					System.out.print("Data not found:" + accountEntity.getUsername());
					continue;
				}

				dataEntity = optional.get();
			}
			datas.add(new Data(accountEntity, dataEntity));
		}

		return datas;
	}

	@Override
	public ArrayList<Data> find(Data data) throws Exception {

//		DataEntity dataEntity = new DataEntity(data);
//
////		dataEntity.setAddress(null);
////		dataEntity.setAvatar(null);
////		dataEntity.setDegree(null);
//		if (dataEntity.getGender() == Gender.None)
//			dataEntity.setGender(null);
////		dataEntity.setId(null);
////		dataEntity.setIdAccount(null);
////		if (!StringUtils.hasText(dataEntity.getSpecialty()))
////			dataEntity.setSpecialty(null);
////		dataEntity.setStudentId(null);
////		dataEntity.setTelephone(null);
//
//		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase(true)
//				.withStringMatcher(StringMatcher.CONTAINING)
////				.withMatcher("specialty", GenericPropertyMatcher.of(StringMatcher.CONTAINING, false))
//				.withMatcher("gender", GenericPropertyMatcher.of(StringMatcher.EXACT, false))
//				.withIgnorePaths("address", "avatar", "degree", "id", "idAccount", "studentId", "telephone");
//
//		Example<DataEntity> example = Example.of(dataEntity, exampleMatcher);
//
//		ArrayList<DataEntity> dataEntities = new ArrayList<>(dataRepository.findAll(example));

		ArrayList<Data> allDatas = getAll(data);

		ArrayList<Data> rsDatas = new ArrayList<>();
		for (Data d : allDatas) {

			if (StringUtils.hasText(data.getSpecialty()))
				if (!StringUtils.hasText(d.getSpecialty())
						|| !d.getSpecialty().toLowerCase().contains(data.getSpecialty().toLowerCase()))
					continue;

			if (data.getGender() != null && data.getGender() != Gender.None)
				if (d.getGender() == null || data.getGender() != d.getGender())
					continue;

			rsDatas.add(d);
		}

		return rsDatas;
	}

}
