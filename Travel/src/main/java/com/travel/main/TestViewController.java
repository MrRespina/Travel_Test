package com.travel.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.travel.main.repository.Practice;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001" }) 
@Controller
public class TestViewController {
	
	@Autowired
	TestService tService;
	
	@GetMapping("/api/JoinMember")
	public String joinMembers(Model model) {
		
		return "joinMember";
		
	}
	
	@GetMapping("/api/updateMember/{id}")
	public String updateMembers(@PathVariable(name="id") int id,Model model) {
		
		Practice p = tService.getPractice(id);
		model.addAttribute("id",p.getId());
		model.addAttribute("name",p.getName());
		return "updateMember";
		
	}

}
