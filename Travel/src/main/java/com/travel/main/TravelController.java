package com.travel.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TravelController {
	
	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("data","hello!");
		return "index";
		
	}
	
	// html include를 위해서 필요헌 페이지를 매핑해주었음.
	@GetMapping("/firstPage.html")
	public String getCategory1() {
		return "firstPage";
	}
	
	@GetMapping("/joinMember.html")
	public String getCategory2() {
		return "joinMember";
	}

	
}
