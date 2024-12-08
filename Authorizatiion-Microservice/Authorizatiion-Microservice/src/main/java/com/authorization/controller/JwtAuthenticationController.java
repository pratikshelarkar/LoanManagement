package com.authorization.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.config.JwtTokenUtil;
import com.authorization.exception.AuthorizationException;
import com.authorization.model.JwtRequest;
import com.authorization.model.JwtResponse;
import com.authorization.model.User;
import com.authorization.repository.UserDao;
import com.authorization.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
//@RequestMapping("/auth")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserDao userDao;

	/**
	 * @param authenticationRequest
	 * @return
	 * @throws AuthorizationException
	 * @throws Exception
	 */
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws AuthorizationException {
		System.out.println("Inside controller");
		// Authentication auth=authenticate(authenticationRequest.getUserName(),
		// authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		System.out.println("authenticate......" + userDetails);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
		// return ResponseEntity.ok(auth);
	}

	private Authentication authenticate(String userName, String password) throws AuthorizationException {
		try {
			System.out.println("Inside authenticate Method==================================");
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			System.out.println("Authentication Successful.....");
			System.out.println(auth.getCredentials() + "+++++++++++++++++++++++++++++++++");
			return auth;

		} catch (DisabledException e) {
			throw new AuthorizationException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new AuthorizationException("INVALID_CREDENTIALS");
		}

	}

	/**
	 * @param requestTokenHeader
	 * @return
	 */
	@PostMapping(value = "/authorize")
	public boolean authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		System.out.println("Inside authorize ==============" + requestTokenHeader);
		String jwtToken = null;
		String userName = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			System.out.println("JWT Tocken =======================" + jwtToken);
			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				return false;
			}
		}
		return userName != null;

	}

	@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody JwtRequest authenticationRequest
    ) {

        try {
        	User user = new User();
        	user.setUserName(authenticationRequest.getUserName());
        	user.setPassword(authenticationRequest.getPassword());
        	
            User registeredUser = userDao.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
	
//	@GetMapping("/movie-catalog")
//	public ResponseEntity<String> movieInfo(
//			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
//			throws AuthorizationException {
//		if (authorizeTheRequest(requestTokenHeader)) {
//			return new ResponseEntity<>("User authenticated", HttpStatus.OK);
//		} else {
//			throw new AuthorizationException("Not allowed");
//		}
//		// return new ResponseEntity<>("User authenticated", HttpStatus.OK);
//
//	}
}