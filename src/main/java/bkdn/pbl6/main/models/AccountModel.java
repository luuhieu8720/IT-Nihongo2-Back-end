package bkdn.pbl6.main.models;

public class AccountModel {

	private String email;
	private String token;

	public AccountModel(String email, String token) {
		this.email = email;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AccountModel [email=" + email + ", token=" + token + "]";
	}

}
