package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(path = "/post/get/all", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<ApiResponse> apiGetAllPost() {
		return getAllPost();
	}

	public ResponseEntity<ApiResponse> getAllPost() {
		return ResponseEntity.ok(new ApiResponse(true, postService.getAll()));
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
