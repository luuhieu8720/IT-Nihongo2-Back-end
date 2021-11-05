package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group_chat_member")
public class GroupChatMemberEntity {

	@Id
	private String id;

	private String idAccount;

	private String idGroup;

	private Integer seenIndex;

	public GroupChatMemberEntity() {
	}

	public GroupChatMemberEntity(String id, String idAccount, String idGroup, Integer seenIndex) {
		this.id = id;
		this.idAccount = idAccount;
		this.idGroup = idGroup;
		this.seenIndex = seenIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public String getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(String idGroup) {
		this.idGroup = idGroup;
	}

	public Integer getSeenIndex() {
		return seenIndex;
	}

	public void setSeenIndex(Integer seenIndex) {
		this.seenIndex = seenIndex;
	}

}
