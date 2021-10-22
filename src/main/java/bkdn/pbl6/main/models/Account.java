package bkdn.pbl6.main.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.enums.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	private String username;
	private String email;
	private String password;
	private String name;
	private Role role;
	private String token;

	public Account() {
	}

	public Account(String username, String email, String password, String name, Role role, String token) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
		this.token = token;
	}

	public Account(AccountModel accountModel) {
		this(accountModel.getAccount());
	}

	public Account(AccountEntity accountEntity) {
		this(accountEntity.getUsername(), accountEntity.getEmail(), "", accountEntity.getName(),
				accountEntity.getRole(), "");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", role=" + role + ", token=" + token + "]";
	}

}
