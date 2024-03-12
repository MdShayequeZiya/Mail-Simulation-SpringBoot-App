package com.ff.mailsimulationapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ff.mailsimulationapp.dto.ResponseStructure;
import com.ff.mailsimulationapp.entity.User;
import com.ff.mailsimulationapp.exception.MailFailedToSentException;
import com.ff.mailsimulationapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<User>> saveUser(@Valid @RequestBody User user) {
		
		return userService.createUser(user);
	}
	
	@PostMapping("/login")
	ResponseEntity<ResponseStructure<User>> login(@Valid @RequestParam String email, @Valid @RequestParam String password, HttpServletRequest request) {
		return userService.login(email, password, request);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			
			session.invalidate();
			return ResponseEntity.ok("Logout successful");
			
		}

	    throw new MailFailedToSentException("Login first!");
	}

}
