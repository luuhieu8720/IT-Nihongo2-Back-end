package bkdn.pbl6.main.entities;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
public class ChatEntity {

	@Id
	private String id;

	private Integer index;

	private String idAccount;

	private String content;

	private Timestamp sendTime;

	public ChatEntity() {
	}

	public ChatEntity(String id, Integer index, String idAccount, String content, Timestamp sendTime) {
		this.id = id;
		this.index = index;
		this.idAccount = idAccount;
		this.content = content;
		this.sendTime = sendTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

}
