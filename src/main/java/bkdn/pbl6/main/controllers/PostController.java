package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.models.Post;
import bkdn.pbl6.main.services.PostService;

@RestController
@RequestMapping(path = "/api/auth/")
@PreAuthorize(value = "hasAnyAuthority('USER', 'TUTOR', 'ADMIN')")
public class PostController {

	@Autowired
	private PostService postService;

	@RequestMapping(path = "/post/new", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<ApiResponse> apiNewPost(@RequestBody Post post) {
		AccountModel accountModel = getAccount();
		post.setUsername(accountModel.getUsername());
		return newPost(post);
	}

	public ResponseEntity<ApiResponse> newPost(Post post) {
		try {
			post = postService.newPost(post);
			return ResponseEntity.ok(new ApiResponse(true, post));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	@RequestMapping(path = "/post/get", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<ApiResponse> apiGetAllPost(@RequestParam @Nullable String id,
			@RequestBody @Nullable Post post) {
		if (post != null) {
			return getPost(post.getId());
		}
		return getPost(id);
	}

	private ResponseEntity<ApiResponse> getPost(String id) {
		if (id == null)
			return ResponseEntity.ok(new ApiResponse(true, postService.getAll()));
		else
			try {
				return ResponseEntity.ok(new ApiResponse(true, postService.get(id)));
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
			}
	}

	@RequestMapping(path = "/post/find", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<ApiResponse> apiFindPost(@RequestBody Post post) {
		return findPost(post);
	}

	private ResponseEntity<ApiResponse> findPost(Post post) {
		if (post == null)
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Use get if you want get all!"));
		try {
			return ResponseEntity.ok(new ApiResponse(true, postService.find(post)));
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
