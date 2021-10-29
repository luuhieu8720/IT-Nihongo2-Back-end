package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.services.AdminService;

@RestController
@RequestMapping(path = "/api/admin")
@PreAuthorize(value = "hasAnyAuthority('ADMIN')")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping(path = "/block")
	public ResponseEntity<ApiResponse> apiBlock(@RequestParam String username) {
		return block(username);
	}

	@PostMapping(path = "/block")
	public ResponseEntity<ApiResponse> apiBlock(@RequestParam @Nullable String username,
			@RequestBody @Nullable Account account) {
		if (account == null) {
			return apiBlock(username);
		}
		return apiBlock(account.getUsername());
	}

	private ResponseEntity<ApiResponse> block(String username) {
		try {
			adminService.enable(username, false);
			return ResponseEntity.ok(new ApiResponse(true));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

}
