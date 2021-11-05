package bkdn.pbl6.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.models.ApiResponse;
import bkdn.pbl6.main.models.Report;
import bkdn.pbl6.main.services.ReportService;

@RestController
@RequestMapping(path = "/api/auth/report")
public class ReportController {

	private static final String errorBlank = "%1$s must not be blank!";

	@Autowired
	private ReportService reportService;

	@RequestMapping(path = "/new", method = { RequestMethod.PUT, RequestMethod.POST })
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	public ResponseEntity<ApiResponse> apiNewReport(@RequestBody Report report) {
		AccountModel accountModel = getAccount();
		report.setUserName(accountModel.getUsername());
		return newReport(report);
	}

	@RequestMapping(path = "/get", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize(value = "hasAnyAuthority('USER', 'TUTOR', 'ADMIN')")
	public ResponseEntity<ApiResponse> apiGetReport(@RequestParam @Nullable String id,
			@RequestBody @Nullable Report report) {
		if (report != null) {
			return getReport(report.getId());
		}
		return getReport(id);
	}

	@RequestMapping(path = "/delete", method = { RequestMethod.POST, RequestMethod.DELETE })
	@PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
	public ResponseEntity<ApiResponse> apiDeleteReport(@RequestParam @Nullable String id,
			@RequestBody @Nullable Report report) {
		AccountModel accountModel = getAccount();
		if (report != null) {
			return deleteReport(report.getId(), accountModel);
		}
		return deleteReport(id, accountModel);
	}

	@RequestMapping(path = "/find", method = { RequestMethod.POST, RequestMethod.GET })
	@PreAuthorize(value = "hasAnyAuthority('USER', 'TUTOR', 'ADMIN')")
	public ResponseEntity<ApiResponse> apiFindReport(@RequestBody Report report) {
		return findReport(report);
	}

	private ResponseEntity<ApiResponse> newReport(Report report) {
		if (report == null) {
			return ResponseEntity.badRequest().body(new ApiResponse(false));
		}
		if (!StringUtils.hasText(report.getTutorName())) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, errorBlank.formatted("Tutor")));
		}
		try {
			report = reportService.newReport(report);
			return ResponseEntity.ok(new ApiResponse(true, report));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	private ResponseEntity<ApiResponse> getReport(String id) {
		if (id == null) {
			return ResponseEntity.ok(new ApiResponse(true, reportService.getAll()));
		} else {
			try {
				return ResponseEntity.ok(new ApiResponse(true, reportService.get(id)));
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
			}
		}
	}

	private ResponseEntity<ApiResponse> deleteReport(String id, AccountModel accountModel) {
		if (!StringUtils.hasText(id)) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, errorBlank.formatted("Id")));
		}
		try {
			reportService.delete(id, accountModel);
			return ResponseEntity.ok(new ApiResponse(true, "Delete successfully!"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	private ResponseEntity<ApiResponse> findReport(Report report) {
		if (report == null) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Use get if you want get all!"));
		}
		try {
			return ResponseEntity.ok(new ApiResponse(true, reportService.find(report)));
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
