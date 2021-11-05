package bkdn.pbl6.main.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.models.Chat;
import bkdn.pbl6.main.models.GroupChat;
import bkdn.pbl6.main.models.Member;
import bkdn.pbl6.main.services.ChatService;

@Controller
//@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN', 'TUTOR')")
public class ChatController {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private ChatService chatService;

	@MessageMapping(value = "/groupchat/get")
	public void socketGetGroupchat(@Nullable GroupChat groupChat, Principal principal) {
		if (groupChat != null) {

		}
		ApiResponse api = getGroupchat(principal.getName());
		template.convertAndSendToUser(principal.getName(), "/queue/", api);
		return;
	}

	private ApiResponse getGroupchat(String username) {
		try {
			ArrayList<GroupChat> groupChats = chatService.getAllGroup(username);
			return new ApiResponse(true, groupChats);
		} catch (Exception e) {
			return new ApiResponse(false, e.getMessage());
		}
	}

	@MessageMapping(value = "/groupchat/new")
	public void socketNewGroupchat(GroupChat groupChat, Principal principal) {
		boolean hasMe = false;
		for (Member member : groupChat.getMembers())
			if (member.getUsername().equals(principal.getName()))
				hasMe = true;
		if (!hasMe) {
			Member me = new Member(principal.getName(), 0);
			groupChat.getMembers().add(me);
		}
		ApiResponse api = newGroupchat(groupChat);
		template.convertAndSendToUser(principal.getName(), "/queue/", api);
		return;
	}

	private ApiResponse newGroupchat(GroupChat groupChat) {
		try {
			groupChat = chatService.newGroup(groupChat);
			return new ApiResponse(true, groupChat);
		} catch (Exception e) {
			return new ApiResponse(false, e.getMessage());
		}
	}

	@MessageMapping(value = "/chat/send")
	public void socketSendChat(Chat chat, Principal principal) {

		return;
	}

}
