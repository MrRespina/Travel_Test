package com.travel.main;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.travel.main.repository.Practice;
import com.travel.main.repository.PracticeMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class TestService {
	
	@Autowired
	PracticeMapper practicemapper;
	
	public Practice getPractice(int id) {
		
		return practicemapper.selectById(id);
		
	}
	
	public List<Practice> getAllPractice() {
		
		List<Practice> praclist = practicemapper.selectAll();
		return praclist;
		
	}
	
	public void joinMyMembership(Practice prac) {
		
		Practice p = new Practice();
		p.setName(prac.getName());
		p.setInfo(prac.getInfo());
		practicemapper.joinMembership(p);
		
	}
	
	public void updateMyInfo(Practice p) {
		
		practicemapper.updateMember(p);
		
	}
	
	public String[] getYoutube(String cityName) throws UnsupportedEncodingException, ParseException{
		
		GoogleApiManager gManager = new GoogleApiManager();
		String[] res = gManager.fetch(cityName);
		return res;
		
	}
	

}
