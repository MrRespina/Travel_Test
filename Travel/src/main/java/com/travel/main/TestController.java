package com.travel.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.travel.main.repository.Practice;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001" }) 
@RestController
public class TestController {
	
	@Autowired
	TestService tService;
	
	@GetMapping("/api/test/{id}")
	public Practice getP(@PathVariable(name="id") int id) {
		return tService.getPractice(id);
	}
	
	@PostMapping("/api/updateMember/update")
	public List<Practice> updateMember(@RequestParam("info") String info, @RequestParam("id") int id) {
		Practice p = new Practice();
		p.setId(id);
		p.setInfo(info);
		tService.updateMyInfo(p);
		return tService.getAllPractice();
	}
	
	@GetMapping("/api/test")
	public List<Practice> getAllP() {
		
		List<Practice> plist = tService.getAllPractice();
		
		return plist;
			
	}
	
	@PostMapping("/api/join")
	public List<Practice> showMyName(Practice p) {
		
		System.out.println(p.getName()+" , "+p.getInfo());
		tService.joinMyMembership(p);
		List<Practice> plist = tService.getAllPractice(); 
		
		return plist;
		
	}

}
