package com.jwt.secjwt.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.jwt.secjwt.jwt.JWTUtil;
import com.jwt.secjwt.model.AuthenticationRequest;
import com.jwt.secjwt.model.AuthenticationResponse;

@RestController
public class HomeController {
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserDetailsService service;

	@Autowired
	private JWTUtil util;

	@RequestMapping(value = "/home")
	public String home() {
		System.out.println("home()");
		return "home";
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("invalid username and password userName:" + request.getUserName() + ", pwd:"
					+ request.getPassword());
		}
		UserDetails ud = service.loadUserByUsername(request.getUserName());
		String token = util.generateToken(ud);

		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
}
