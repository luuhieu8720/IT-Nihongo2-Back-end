package bkdn.pbl6.main.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<ApiResponse> apiNewGroupChat(@RequestBody GroupChat groupChat, Principal principal) {
		boolean hasMe = false;
		for (Member member : groupChat.getMembers())
			if (member.getUsername().equals(principal.getName()))
				hasMe = true;
		if (!hasMe) {
			Member me = new Member(principal.getName(), 0);
			groupChat.getMembers().add(me);
		}
		return newGroupchat(groupChat);
		// curl --request POST "http://localhost:8080/api/chat/group/new" --header
		// "Authorization: Bearer
		// eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MTZiOTZiOTI1MTI0YTJkMmY0ZmY5YjciLCJpYXQiOjE2MzQ0NTM1OTYsImV4cCI6MTY0MjIyOTU5Nn0.vDIizhNtuv2NjJ28Kg2CUZAGarCrLuPV2Hzp2ToXo55akSzgwoHhUsRFym9uzjXChxlYxTfqKANZNLcKHTgxYg"
		// --header "Content-Type: application/json" --data "{\"id\":\"sd\",
		// \"members\":[{\"username\":\"ad\"}]}"
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
