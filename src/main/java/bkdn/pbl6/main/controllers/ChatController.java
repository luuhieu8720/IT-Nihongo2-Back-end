package bkdn.pbl6.main.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.models.Chat;
import bkdn.pbl6.main.models.GroupChat;
import bkdn.pbl6.main.models.Member;
import bkdn.pbl6.main.services.ChatService;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private ChatService chatService;

	@MessageMapping(value = "/chat/group/all")
	public void socketGetGroupchat(Principal principal) {
		ApiResponse response = getGroupchat(principal.getName());
		template.convertAndSendToUser(principal.getName(), "/queue/chat/group/all", response);
		return;
	}

	private ApiResponse getGroupchat(String username) {
		try {
			ArrayList<GroupChat> groupChats = chatService.getAllGroup(username);
			return new ApiResponse(true, groupChats);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	@MessageMapping(value = "/chat/group")
	public void socketGetGroupchat(GroupChat groupChat, Principal principal) {
		ApiResponse response = getGroupChat(groupChat);
		template.convertAndSendToUser(principal.getName(), "/queue/chat/group", response);
		return;
	}

	private ApiResponse getGroupChat(GroupChat groupChat) {
		try {
			groupChat = chatService.getGroup(groupChat);
			return new ApiResponse(true, groupChat);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	@MessageMapping(value = "/chat/group/new")
	public void socketNewGroupchat(GroupChat groupChat, Principal principal) {
		boolean hasMe = false;
		for (Member member : groupChat.getMembers())
			if (member.getUsername().equals(principal.getName()))
				hasMe = true;
		if (!hasMe) {
			Member me = new Member(principal.getName(), 0);
			groupChat.getMembers().add(me);
		}
		ApiResponse response = newGroupchat(groupChat);
		template.convertAndSendToUser(principal.getName(), "/queue/chat/group/new", response);
		return;
	}

	private ApiResponse newGroupchat(GroupChat groupChat) {
		try {
			groupChat = chatService.newGroup(groupChat);
			return new ApiResponse(true, groupChat);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

	@MessageMapping(value = "/chat/send")
	public void socketSendChat(Chat chat, Principal principal) {
		try {
			chat.setUsername(principal.getName());
			ApiResponse sendChat = sendChat(chat);
			if (sendChat.getSuccess()) {
				chat = (Chat) sendChat.getValue();
				GroupChat groupChat = new GroupChat();
				groupChat.setId(chat.getIdGroup());
				groupChat = chatService.getGroup(groupChat);
				for (Member member : groupChat.getMembers()) {
					template.convertAndSendToUser(member.getUsername(), "/queue/chat/receive", chat);
				}
			}
		} catch (Exception e) {
			template.convertAndSendToUser(principal.getName(), "/queue/chat/receive", e.getMessage());
		}
		return;
	}

	private ApiResponse sendChat(Chat chat) {
		try {
			chat = chatService.sendChat(chat);
			return new ApiResponse(true, chat);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(false, e.getMessage());
		}
	}

}
