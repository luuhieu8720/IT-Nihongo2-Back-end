package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.entities.Role;
import bkdn.pbl6.main.jwt.JwtTokenProvider;
import bkdn.pbl6.main.models.Account;
import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.services.MailService;
import bkdn.pbl6.main.services.UserService;

@RestController
@RequestMapping("/api/noauth")
public class HomeController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private MailService mailService;

	@Autowired
	private UserService userService;

	@GetMapping("/hello")
	public ApiResponse apiHello() {
		return new ApiResponse(true, "hello api");
	}

	@GetMapping(path = { "/login", "/signin" })
	public ResponseEntity<ApiResponse> apiSignin(@RequestParam String username, @RequestParam String password) {
		return signin(username, password);
	}

	@PostMapping(path = { "/login", "/signin" })
	public ResponseEntity<ApiResponse> apiSignin(@RequestParam @Nullable String username,
			@RequestParam @Nullable String password, @RequestBody @Nullable Account account) {
		if (account == null) {
			return apiSignin(username, password);
		}
		return apiSignin(account.getUsername(), account.getPassword());
	}

	private ResponseEntity<ApiResponse> signin(String username, String password) {
		if (!StringUtils.hasText(username)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Username must not be blank!"));
		}
		if (!StringUtils.hasText(password)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Password must not be blank!"));
		}
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			AccountModel principal = (AccountModel) authentication.getPrincipal();
			String token = tokenProvider.generateToken(principal);
			Account account = new Account(principal);
			account.setToken(token);
			return ResponseEntity.ok(new ApiResponse(true, account));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

//	@PostMapping("/api/logout")
//	public ResponseEntity<ApiResponse> apiLogout(@RequestParam String email, @RequestParam String token) {
//		return logout(email, token);
//	}
//
//	@PostMapping("/api/signout")
//	public ResponseEntity<ApiResponse> apiSignout(@RequestParam String email, @RequestParam String token) {
//		return logout(email, token);
//	}

//	@RequestMapping(path = "/api/logout", method = { RequestMethod.GET, RequestMethod.POST })
//	public ResponseEntity<ApiResponse> apiLogout(AccountModel model) {
//		return logout(model);
//	}
//
//	@RequestMapping(path = "/api/signout", method = { RequestMethod.GET, RequestMethod.POST })
//	public ResponseEntity<ApiResponse> apiSignout(AccountModel model) {
//		return logout(model);
//	}

//	private ResponseEntity<ApiResponse> logout(String email, String token) {
//		System.out.println(email);
//		System.out.println(token);
//		if (email == null || email.isBlank() || token == null || token.isBlank()) {
//			return ResponseEntity.badRequest().body(new ApiResponse(false, "Blank"));
//		}
//		Boolean succes = authenticationService.logout(email, token);
//		if (succes) {
//			return ResponseEntity.ok(new ApiResponse(true, "Logout succes"));
//		} else {
//			return ResponseEntity.badRequest().body(new ApiResponse(false, "Authentication fail!"));
//		}
//	}

	@GetMapping(path = { "/logup", "signup" })
	public ResponseEntity<ApiResponse> apiSignup(@RequestParam @Nullable String username, @RequestParam String email,
			@RequestParam String password, @RequestParam String name, @RequestParam @Nullable Role role) {
		if (role == null) {
			role = Role.User;
		}
		if (!StringUtils.hasText(username)) {
			username = email.substring(0, email.indexOf('@'));
		}
		return signup(username, email, password, name, role);
	}

	@PostMapping(path = { "/logup", "/signup" })
	public ResponseEntity<ApiResponse> apiSignup(@RequestParam @Nullable String username,
			@RequestParam @Nullable String email, @RequestParam @Nullable String password,
			@RequestParam @Nullable Role role, @RequestParam @Nullable String name, @RequestBody @Nullable Account account) {
		if (account == null) {
			return apiSignup(username, email, password, name, role);
		}
		return apiSignup(account.getUsername(), account.getEmail(), account.getPassword(), account.getName(), account.getRole());
	}

	private ResponseEntity<ApiResponse> signup(String username, String email, String password, String name, Role role) {
		if (!StringUtils.hasText(email)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Email must not be blank!"));
		}
		if (!StringUtils.hasText(password)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Password must not be blank!"));
		}
		if (!StringUtils.hasText(name)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Name must not be blank!"));
		}
		try {
			Account account = new Account(username, email, password, name, role, null);
			account = userService.signup(account);
			return ResponseEntity.ok(new ApiResponse(true, account));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

//	@PostMapping(path = "/signup/finish")
//	public ResponseEntity<ApiResponse> apiSignupFinish(@RequestBody Account account) {
//		return signupFinish(account.getToken());
//	}
//
//	private ResponseEntity<ApiResponse> signupFinish(String token) {
//		if (!StringUtils.hasText(token)) {
//			return null;
//		}
//		try {
//			Account account = userService.signupFinish(token);
//			return ResponseEntity.ok(new ApiResponse(true, account));
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
//		}
//	}
//
//	@GetMapping(path = { "/signup/re", "/logup/re" })
//	public ResponseEntity<ApiResponse> apiSignupRe(@RequestParam String email) {
//		return signupRe(email);
//	}
//
//	@PostMapping(path = { "signup/re", "/logup/re" })
//	public ResponseEntity<ApiResponse> apiSignupRe(@RequestParam @Nullable String email,
//			@RequestBody @Nullable Account account) {
//		if (account == null) {
//			return apiSignupRe(email);
//		}
//		return apiSignupRe(account.getEmail());
//	}
//
//	private ResponseEntity<ApiResponse> signupRe(String email) {
//		if (!StringUtils.hasText(email)) {
//			return ResponseEntity.badRequest().body(new ApiResponse(false, "Blank"));
//		}
//		try {
//			Account account = userService.signupRe(email);
//			return ResponseEntity.ok(new ApiResponse(true, account));
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
//		}
//	}

//	@RequestMapping(path = "/api/forgotpassword", method = { RequestMethod.GET, RequestMethod.POST })
//	public ResponseEntity<ApiResponse> apiForgotpassword(@RequestParam String email) {
//		return forgotpassword(email);
//	}

//	private ResponseEntity<ApiResponse> forgotpassword(String email) {
//		return ResponseEntity.ok(new ApiResponse(true, email));
//	}

	@GetMapping(path = "/mail/hello")
	public ResponseEntity<ApiResponse> mailHello(@RequestParam String email) {
		mailService.sendHello(email);
		return ResponseEntity.ok(new ApiResponse(true, ' '));
	}

}
