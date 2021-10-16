package bkdn.pbl6.main.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.models.ApiResponse;

@Controller
public class TestController {

	@GetMapping(path = "/api/test")
	@PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN', 'TUTOR')")
	public ResponseEntity<ApiResponse> testAuth() {
		return ResponseEntity.ok(new ApiResponse(true, "Phai sign in thi moi thay!"));
	}

	@GetMapping(path = "/api/noauth/signup/test")
	public String testSignup(ModelMap map) {
		map.addAttribute("user", new Account(null, null, null, null, null, "token"));
		return "mail/signup";
	}

}
