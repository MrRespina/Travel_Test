package com.travel.jo.user;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	@GetMapping("/signup")
	public String singup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "signup_form";
		}
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) {
			// 중복된 데이터에 대한 예외 처리
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			// DataIntegrityViolationException 외에 다른 예외 처리
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
	@GetMapping("/delete")
	public String delete(Principal principal) {
		this.userService.deleteUser(principal);
		return "redirect:/user/logout";
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping("/modify")
	public String userModify(UserCreateForm userCreateForm, Principal principal,Model model) {
		SiteUser siteUser = this.userService.getUser(principal.getName());
		userCreateForm.setUsername(siteUser.getUsername());
		userCreateForm.setEmail(siteUser.getEmail());
		return "user_info_form";
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping("/modify")
	public String userModify(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, 
			Principal principal, Model model) {
		// 변경 필요 수정버튼 할꺼
		
		if (bindingResult.hasErrors()) {
			return "user_info_form";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "user_info_form";
		}
		this.userService.update(principal, userCreateForm.getPassword1(), userCreateForm.getEmail());
		
		model.addAttribute("r", "회원정보 수정 완료");
		return "user_info_form";
	}
}
