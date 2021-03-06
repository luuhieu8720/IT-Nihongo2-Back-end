package bkdn.pbl6.main.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.models.GroupChat;
import bkdn.pbl6.main.models.Member;
import bkdn.pbl6.main.services.ChatService;

@RestController
@RequestMapping(path = "/api/chat")
@PreAuthorize(value = "hasAnyAuthority('USER', 'TUTOR', 'ADMIN')")
public class ApiChatController {

	@Autowired
	private ChatService chatService;

	@RequestMapping(path = "/group/new", method = { RequestMethod.POST })
	public ResponseEntity<ApiResponse> apiNewGroupChat(GroupChat groupChat, Principal principal) {
		boolean hasMe = false;
		for (Member member : groupChat.getMembers())
			if (member.getUsername().equals(principal.getName()))
				hasMe = true;
		if (!hasMe) {
			Member me = new Member(principal.getName(), 0);
			groupChat.getMembers().add(me);
		}
		return newGroupchat(groupChat);
	}

	private ResponseEntity<ApiResponse> newGroupchat(GroupChat groupChat) {
		try {
			groupChat = chatService.newGroup(groupChat);
			return ResponseEntity.ok(new ApiResponse(true, groupChat));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

}
