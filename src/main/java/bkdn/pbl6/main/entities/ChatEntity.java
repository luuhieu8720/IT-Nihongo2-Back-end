package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.models.Chat;

@Document(collection = "chat")
public class ChatEntity {

	@Id
	private String id;

	private String idGroup;

	private Integer index;

	private String idAccount;

	private String content;

	private Long sendTime;

	public ChatEntity() {
	}

	public ChatEntity(String id, String idGroup, Integer index, String idAccount, String content, Long sendTime) {
		this.id = id;
		this.idGroup = idGroup;
		this.index = index;
		this.idAccount = idAccount;
		this.content = content;
		this.sendTime = sendTime;
	}

	public ChatEntity(Chat chat) {
		this.idGroup = chat.getIdGroup();
		this.content = chat.getContent();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

}
