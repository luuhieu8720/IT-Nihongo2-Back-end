package bkdn.pbl6.main.services;

import java.util.ArrayList;

import bkdn.pbl6.main.models.Chat;
import bkdn.pbl6.main.models.GroupChat;

public interface ChatService {

	public ArrayList<GroupChat> getAllGroup(String username) throws Exception;

	public GroupChat newGroup(GroupChat groupChat) throws Exception;

	public GroupChat getGroup(GroupChat groupChat) throws Exception;

	public Chat sendChat(Chat chat) throws Exception;

}
