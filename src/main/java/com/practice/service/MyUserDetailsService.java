package com.practice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.models.MyUserDetails;
import com.practice.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private SecurityService securityService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = securityService.getByUserName(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: "+username));
		
		return user.map(MyUserDetails::new).get();
	}

}
