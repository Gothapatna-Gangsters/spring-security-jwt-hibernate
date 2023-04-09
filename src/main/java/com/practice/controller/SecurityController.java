package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.practice.models.AuthenticationRequest;
import com.practice.models.AuthenticationResponse;
import com.practice.service.MyUserDetailsService;
import com.practice.util.JwtUtil;

@RestController
public class SecurityController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch(BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.ok("Bad credentials, wrong password");
//			throw new Exception("Incorrect username or password "+e);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok("Bad credentials, wrong username");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@GetMapping("/home")
	public String home() {
		return ("<h1> Welcome all user </h1>");
	}
	
	@GetMapping("/test")
	public String test() {
		return ("<h1> Welcome all logged in user </h1>");
	}
	
	@GetMapping("/user")
	public String user() {
		return ("<h1> Welcome user </h1>");
	}
	
	@GetMapping("/admin")
	public String admin() {
		return ("<h1> Welcome admin </h1>");
	}

}
