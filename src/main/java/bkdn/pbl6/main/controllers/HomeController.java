package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.models.AccountModel;
import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.services.AuthenticationService;

@RestController
public class HomeController {

	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping("/api/hello")
	public ApiResponse apiHello() {
		return new ApiResponse(true, "hello api");
	}

	@PostMapping("/api/login")
	public ResponseEntity<ApiResponse> apiLogin(@RequestParam String email, @RequestParam String password) {
		return login(email, password);
	}

	@PostMapping("/api/signin")
	public ResponseEntity<ApiResponse> apiSignin(@RequestParam String email, @RequestParam String password) {
		return login(email, password);
	}

	private ResponseEntity<ApiResponse> login(String email, String pass) {
		if (email == null || email.isBlank() || pass == null || pass.isBlank()) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Blank"));
		}
		AccountModel model = authenticationService.login(email, pass);
		if (model != null) {
			return ResponseEntity.ok(new ApiResponse(true, model));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Authentication fail!"));
		}
	}

	@PostMapping("/api/logout")
	public ResponseEntity<ApiResponse> apiLogout1(@RequestParam String email, @RequestParam String token) {
		return logout(new AccountModel(email, token));
	}

//	@RequestMapping(path = "/api/logout", method = { RequestMethod.GET, RequestMethod.POST })
//	public ResponseEntity<ApiResponse> apiLogout(AccountModel model) {
//		return logout(model);
//	}
//
//	@RequestMapping(path = "/api/signout", method = { RequestMethod.GET, RequestMethod.POST })
//	public ResponseEntity<ApiResponse> apiSignout(AccountModel model) {
//		return logout(model);
//	}

	private ResponseEntity<ApiResponse> logout(AccountModel model) {
		String email = model.getEmail();
		String token = model.getToken();
		System.out.println(model.toString());
		System.out.println(email);
		System.out.println(token);
		if (email == null || email.isBlank() || token == null || token.isBlank()) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Blank"));
		}
		Boolean succes = authenticationService.logout(model);
		if (succes) {
			return ResponseEntity.ok(new ApiResponse(true, "Logout succes"));
		} else {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Authentication fail!"));
		}
	}

	@RequestMapping(path = "/api/forgotpassword", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<ApiResponse> apiForgotpassword(@RequestParam String email) {
		return forgotpassword(email);
	}

	private ResponseEntity<ApiResponse> forgotpassword(String email) {
		return ResponseEntity.ok(new ApiResponse(true, email));
	}
}
