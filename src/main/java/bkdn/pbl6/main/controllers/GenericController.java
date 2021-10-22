package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.models.Data;
import bkdn.pbl6.main.models.Password;
import bkdn.pbl6.main.services.UserService;

@RestController
@RequestMapping(path = "/api/auth")
@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN', 'TUTOR')")
public class GenericController {

	private final String errorBlank = "%1$s must not be blank!";

	@Autowired
	private UserService userService;

	@GetMapping(path = "/get")
	public ResponseEntity<ApiResponse> apiGet(@RequestParam @Nullable String username) {
		if (StringUtils.hasText(username)) {
			return get(username);
		}
		AccountModel accountModel = getAccount();
		return get(accountModel.getUsername());
	}

	@PostMapping(path = "/get")
	public ResponseEntity<ApiResponse> apiGet(@RequestParam @Nullable String username,
			@RequestBody @Nullable Account account) {
		if (account != null) {
			return apiGet(account.getUsername());
		}
		return apiGet(username);
	}

	private ResponseEntity<ApiResponse> get(String username) {
		if (!StringUtils.hasText(username)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, errorBlank.formatted("Username")));
		}
		try {
			Data data = userService.get(username);
			return ResponseEntity.ok(new ApiResponse(true, data));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	@RequestMapping(path = "/update", method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<ApiResponse> apiUpdate(@RequestBody Data data) {
		AccountModel accountModel = getAccount();
		data.setUsername(accountModel.getUsername());
		return update(data);
	}

	private ResponseEntity<ApiResponse> update(Data data) {
		try {
			data = userService.update(data);
			return ResponseEntity.ok(new ApiResponse(true, data));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	@PutMapping(path = "/update/password")
	public ResponseEntity<ApiResponse> apiUpdatePassword(@RequestBody Password pass) {
		AccountModel accountModel = getAccount();
		return updatePassword(accountModel.getUsername(), pass.getOldPassword(), pass.getNewPassword());
	}

	private ResponseEntity<ApiResponse> updatePassword(String username, String oldPass, String newPass) {
		if (!StringUtils.hasText(newPass)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, errorBlank.formatted("Password")));
		}
		try {
			userService.updatePassword(username, oldPass, newPass);
			return ResponseEntity.ok(new ApiResponse(true));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	private AccountModel getAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			return (AccountModel) authentication.getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
