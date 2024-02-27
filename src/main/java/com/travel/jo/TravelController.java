package com.travel.jo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Controller
public class TravelController {
	
	@GetMapping("/")
	public String root() {
		// 기본 메인페이지로 이동
		return "redirect:/board/list";
	}
}
