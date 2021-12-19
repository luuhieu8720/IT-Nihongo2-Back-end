package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.enums.Type;

@Document(collection = "group_chat")
public class GroupChatEntity {

	@Id
	private String id;

	private String name;

	private Integer length;

	private Type type;

	public GroupChatEntity() {
	}

	public GroupChatEntity(String id, String name, Integer length, Type type) {
		this.id = id;
		this.name = name;
		this.length = length;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
