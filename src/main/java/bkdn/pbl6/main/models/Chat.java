package bkdn.pbl6.main.models;

import java.sql.Timestamp;

public class Chat {

	private String idGroup;

	private String username;

	private String content;

	private Integer index;

	private Timestamp sendTime;

	public Chat() {
	}

	public Chat(String idGroup, String username, String content, Integer index, Timestamp sendTime) {
		this.idGroup = idGroup;
		this.username = username;
		this.content = content;
		this.index = index;
		this.sendTime = sendTime;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

}
