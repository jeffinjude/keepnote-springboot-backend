package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	/**
	 * 
	 */
	private UserRepository userRepo;

	/**
	 * 
	 * @param userRepo
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * 
	 */
	public User registerUser(User user) throws UserAlreadyExistsException {
		// Optional<User> userExists = this.userRepo.findById(user.getUserId());
		User userExists = null;
		try {
			userExists = this.userRepo.findByUserName(user.getUserName());
		} catch (Exception e) {
		}

		if (userExists == null) {
			user.setUserAddedDate(new Date());
			userExists = this.userRepo.insert(user);
		} else {
			throw new UserAlreadyExistsException("User already exists");
		}
		
		if (userExists == null) {
			throw new UserAlreadyExistsException("User creation failed");
		}

		return user;
	}

	/**
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId, User user) throws UserNotFoundException {

		Optional<User> userExists = this.userRepo.findById(userId);
		if (userExists.isPresent()) {
			if(user.getUserId() == null) {
				user.setUserId(userId);
			}
			if(user.getUserName() == null) {
				user.setUserName(userExists.get().getUserName());
			}
			if(user.getUserPassword() == null) {
				user.setUserPassword(userExists.get().getUserPassword());
			}
			if(user.getUserMobile() == null) {
				user.setUserMobile(userExists.get().getUserMobile());
			}
			user.setUserAddedDate(userExists.get().getUserAddedDate());
			this.userRepo.save(user);
		} else {
			throw new UserNotFoundException("user not found");
		}

		return user;
	}

	/**
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {
		Optional<User> userExists = this.userRepo.findById(userId);

		if (!userExists.isPresent() || (userExists.isPresent() && userExists.get() == null)) {
			throw new UserNotFoundException("user not found");
		}
		this.userRepo.deleteById(userId);
		return true;
	}

	/**
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {
		Optional<User> user = this.userRepo.findById(userId);
		if (!user.isPresent() || (user.isPresent() && user.get() == null)) {
			throw new UserNotFoundException("User not found");
		}
		return user.get();
	}
	
}
