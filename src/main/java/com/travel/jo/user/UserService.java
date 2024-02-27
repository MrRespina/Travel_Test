package com.travel.jo.user;

import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travel.jo.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserMapper uMapper;
	private final PasswordEncoder passwordEncoder;

	public void create(String username, String email, String password) {
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(username);
		siteUser.setPassword(passwordEncoder.encode(password));
		siteUser.setEmail(email);
		
		this.uMapper.insertSiteUser(siteUser);

	}
	
	public SiteUser getUser(String username) {
		SiteUser siteUser = this.uMapper.findByUsername(username);
		if (siteUser != null) {
			return siteUser;
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
	
	public void deleteUser(Principal principal) {
		this.uMapper.deleteByUsername(principal.getName());
	}
	
	public void update (Principal principal, String password, String email) {
		SiteUser siteUser = new SiteUser();
		siteUser.setId(this.uMapper.findByUsername(principal.getName()).getId());
		siteUser.setUsername(principal.getName());
		siteUser.setPassword(passwordEncoder.encode(password));
		siteUser.setEmail(email);
		
		System.out.println(siteUser.getPassword());
		this.uMapper.updateByUsername(siteUser);
	}
}
