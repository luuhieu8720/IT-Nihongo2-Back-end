package bkdn.pbl6.main.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.models.ApiResponse;

@RestController
public class HomeController {

	@GetMapping("/api/hello")
	public ApiResponse apiHello() {
		return new ApiResponse("succes", "hello api");
	}
}
