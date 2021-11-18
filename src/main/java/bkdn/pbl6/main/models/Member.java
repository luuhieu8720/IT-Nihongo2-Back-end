package bkdn.pbl6.main.models;

public class Member {

	private String username;

	private Integer seenIndex;

	public Member() {
	}

	public Member(String username, Integer seenIndex) {
		this.username = username;
		this.seenIndex = seenIndex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSeenIndex() {
		return seenIndex;
	}

	public void setSeenIndex(Integer seenIndex) {
		this.seenIndex = seenIndex;
	}

}
