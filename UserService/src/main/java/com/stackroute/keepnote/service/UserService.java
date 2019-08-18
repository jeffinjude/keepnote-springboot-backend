package com.stackroute.keepnote.service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;

public interface UserService {
	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserAlreadyExistsException
	 */
	User registerUser(User user) throws UserAlreadyExistsException;

	/**
	 * 
	 * @param userId
	 * @param user
	 * @return
	 * @throws UserNotFoundException
	 */
	User updateUser(String userId, User user) throws UserNotFoundException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	boolean deleteUser(String userId) throws UserNotFoundException;

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	User getUserById(String userId) throws UserNotFoundException;
}
