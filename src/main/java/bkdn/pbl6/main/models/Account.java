package bkdn.pbl6.main.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	private String nickname;
	private String email;
	private String password;
	private Role role;
	private String token;

	public Account() {
	}

	public Account(String nickname, String email, String password, Role role, String token) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.token = token;
	}

	public Account(AccountModel accountModel) {
		this(accountModel.getAccount());
	}

	public Account(AccountEntity accountEntity) {
		this(accountEntity.getNickname(), accountEntity.getEmail(), "", accountEntity.getRole(), "");
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
		return "AccountModel [nickname=" + nickname + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", token=" + token + "]";
	}

}
