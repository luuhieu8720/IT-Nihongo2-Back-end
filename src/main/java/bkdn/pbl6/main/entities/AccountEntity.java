package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.models.Account;

@Document(collection = "account")
public class AccountEntity {

	@Id
	private String id;

	@Indexed(unique = true)
	private String username;

	@Indexed(unique = true)
	private String email;

	private String password;

	private String name;

	private Boolean enable;

	private Role role;

	private String idData;

	public AccountEntity() {
	}

	public AccountEntity(String id, String username, String email, String password, String name, Boolean enable,
			Role role, String idData) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.enable = enable;
		this.role = role;
		this.idData = idData;
	}

	public AccountEntity(Account account) {
		this(null, account.getUsername(), account.getEmail(), account.getPassword(), account.getName(), true,
				account.getRole(), null);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getIdData() {
		return idData;
	}

	public void setIdData(String idData) {
		this.idData = idData;
	}

	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", name=" + name + ", enable=" + enable + ", role=" + role + ", idData=" + idData + "]";
	}

}
