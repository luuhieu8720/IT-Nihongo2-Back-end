package bkdn.pbl6.main.services;

import java.util.ArrayList;

import bkdn.pbl6.main.models.GroupChat;

public interface ChatService {

	public ArrayList<GroupChat> getAllGroup(String username) throws Exception;

	public GroupChat newGroup(GroupChat groupChat) throws Exception;

}
