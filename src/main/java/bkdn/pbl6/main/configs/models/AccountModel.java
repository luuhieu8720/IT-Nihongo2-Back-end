package bkdn.pbl6.main.configs.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import bkdn.pbl6.main.entities.AccountEntity;

public class AccountModel implements UserDetails {

	private static final long serialVersionUID = 1L;

	private AccountEntity account;
	private Collection<RoleModel> role;

	public AccountModel(AccountEntity account, Collection<RoleModel> role) {
		super();
		this.account = account;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

	public Collection<RoleModel> getRole() {
		return role;
	}

	public void setRole(Collection<RoleModel> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AccountModel [account=" + account + ", role=" + role + "]";
	}

}
