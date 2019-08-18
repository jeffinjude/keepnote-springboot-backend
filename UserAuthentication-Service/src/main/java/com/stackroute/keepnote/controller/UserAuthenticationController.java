package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserAlreadyExistsException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserAuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@CrossOrigin
public class UserAuthenticationController {
	
	private static final long EXPIRATION_TIME = 1800000; // 30 min
	private UserAuthenticationService authicationService;

	/*
	 * Autowiring should be implemented for the UserAuthenticationService. (Use
	 * Constructor-based autowiring) Please note that we should not create an object
	 * using the new keyword
	 */
	@Autowired
	public UserAuthenticationController(UserAuthenticationService authicationService) {
		this.authicationService = authicationService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP
	 * POST method
	 */
	@PostMapping("/api/v1/auth/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		boolean status = false;
		try {
			this.authicationService.saveUser(user);
			status = true;
		} catch (UserAlreadyExistsException e) {
		}
		return status ? new ResponseEntity<>(user, HttpStatus.CREATED)
				: new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
	}

	/*
	 * Define a handler method which will authenticate a user by reading the
	 * Serialized user object from request body containing the username and
	 * password. The username and password should be validated before proceeding
	 * ahead with JWT token generation. The user credentials will be validated
	 * against the database entries. The error should be return if validation is not
	 * successful. If credentials are validated successfully, then JWT token will be
	 * generated. The token should be returned back to the caller along with the API
	 * response. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If login is successful 2.
	 * 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP
	 * POST method
	 */
	@PostMapping("/api/v1/auth/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) throws Exception {
		User userExists = null;
		ResponseEntity<?> res = null;
		Map<String,String> tokenResponse = new HashMap<String,String>();
		try {
			userExists = this.authicationService.findByUserIdAndPassword(user.getUserId(), user.getUserPassword());
		}
		catch(UserNotFoundException e) {
			res = new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		
		if(userExists != null) {
			tokenResponse.put("token", getToken(user.getUserId(), user.getUserPassword()));
			tokenResponse.put("message", "User successfully logged in");
			res = new ResponseEntity<Map>(tokenResponse, HttpStatus.OK);
		}
		else {
			tokenResponse.put("message", "Username or password is incorrect");
			res = new ResponseEntity<Map>(tokenResponse, HttpStatus.UNAUTHORIZED);
		}
		
		return res;
	}
	
	@PostMapping("/api/v1/auth/login/isAuthenticated")
	public ResponseEntity<Map> isAuthenticated(@RequestHeader("Authorization") String authHeader) throws Exception {
		ResponseEntity<Map> res = null;
		Map<String,Object> authRes = new HashMap<String,Object>();
		
		if(authHeader==null || !authHeader.startsWith("Bearer")) {
			throw new ServletException("Invalid request. Please provide correct bearer token.");
		}
		
		String token=authHeader.substring(7); // The first 6 chars of auth header is 'Bearer', after that only we get the token.
		try {
			Claims claim=Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody(); // Extract the claims of JWT token. 
																							//We set subject, issued at and exp in the user auth service jwt token generation.
			authRes.put("isAuthenticated", true);
			authRes.put("username", claim.getSubject());
			res = new ResponseEntity<Map>(authRes, HttpStatus.OK);
		}
		catch(Exception e) {
			authRes.put("isAuthenticated", false);
			authRes.put("username", null);
			res = new ResponseEntity<Map>(authRes, HttpStatus.UNAUTHORIZED);
		}
		
		return res;
	}
	
	// Generate JWT token
	public String getToken(String username, String password) throws Exception {
		//Refer https://medium.com/vandium-software/5-easy-steps-to-understanding-json-web-tokens-jwt-1164c0adfcec
		/*
		 * The content of JWT is called claims. We can set various standard claims like subject
		 * issued at, expiration etc. We can also set custom content also into the claims.
		 */
		String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, "secretKey").compact();
		return jwtToken;
	}

}
