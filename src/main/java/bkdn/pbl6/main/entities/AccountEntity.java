package bkdn.pbl6.main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import bkdn.pbl6.main.models.Account;

@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String nickname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean valication;

	@Column(nullable = false)
	private Role role;

	@Column
	private Integer idData;

	public AccountEntity() {
	}

	public AccountEntity(Integer id, String nickname, String email, String password, Boolean valication, Role role,
			Integer idData) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.valication = valication;
		this.role = role;
		this.idData = idData;
	}

	public AccountEntity(Account account) {
		this.nickname = account.getNickname();
		this.email = account.getEmail();
		this.password = account.getPassword();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Boolean getValication() {
		return valication;
	}

	public void setValication(Boolean valication) {
		this.valication = valication;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getIdData() {
		return idData;
	}

	public void setIdData(Integer idData) {
		this.idData = idData;
	}

	@Override
	public String toString() {
		return "AccountEntity [id=" + id + ", nickname=" + nickname + ", email=" + email + ", password=" + password
				+ ", valication=" + valication + ", role=" + role + ", idData=" + idData + "]";
	}

}
