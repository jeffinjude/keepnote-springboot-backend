package com.stackroute.keepnote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

@RestController
public class UserController {

	/**
	 * 	
	 */
	private UserService userService;

	/**
	 * 
	 * @param userService
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/api/v1/user")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		boolean status = false;
		try {
			this.userService.registerUser(user);
			status = true;
		} catch (UserAlreadyExistsException e) {
		}
		return status ? new ResponseEntity<>(user, HttpStatus.CREATED)
				: new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
	}

	/**
	 * 
	 * @param user
	 * @param id
	 * @return
	 */
	@PutMapping("/api/v1/user/{id}")
	public ResponseEntity<String> modifyUser(@RequestBody User user, @PathVariable("id") String id) {
		boolean status = false;
		try {
			if (this.userService.updateUser(id, user) != null) {
				status = true;
			}
		} catch (UserNotFoundException e) {
		}

		return status ? ResponseEntity.status(HttpStatus.OK).body(" user detail modified")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(" user not found ");
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/v1/user/{id}")
	public ResponseEntity<String> removeUser(@PathVariable("id") String id) {
		boolean status = false;
		try {
			if (this.userService.deleteUser(id)) {
				status = true;
			}
		} catch (UserNotFoundException e) {
		}
		return status ? ResponseEntity.status(HttpStatus.OK).body("deleted user successfully")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found ");
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/api/v1/user/{id}")
	public ResponseEntity<User> getExists(@PathVariable("id") String id) {
		boolean status = false;
		User user = null;
		try {
			user = this.userService.getUserById(id);
			status = true;
		} catch (UserNotFoundException e) {
		}
		return status ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
	}
}
