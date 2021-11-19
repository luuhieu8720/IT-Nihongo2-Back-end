package bkdn.pbl6.main.models;

import java.util.ArrayList;

import bkdn.pbl6.main.entities.GroupChatEntity;

public class GroupChat {

	private String id;

	private String name;

	private ArrayList<Member> members;

	private Integer length;

	private ArrayList<Chat> chats;

	public GroupChat() {
	}

	public GroupChat(String id, String name, ArrayList<Member> members, Integer length, ArrayList<Chat> chats) {
		this.id = id;
		this.name = name;
		this.members = members;
		this.length = length;
		this.chats = chats;
	}

	public GroupChat(GroupChatEntity groupChatEntity) {
		insert(groupChatEntity);
	}

	public void insert(GroupChatEntity groupChatEntity) {
		this.id = groupChatEntity.getId();
		this.name = groupChatEntity.getName();
		this.length = groupChatEntity.getLength();
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

	public ArrayList<Member> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Member> members) {
		this.members = members;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public ArrayList<Chat> getChats() {
		return chats;
	}

	public void setChats(ArrayList<Chat> chats) {
		this.chats = chats;
	}

}
