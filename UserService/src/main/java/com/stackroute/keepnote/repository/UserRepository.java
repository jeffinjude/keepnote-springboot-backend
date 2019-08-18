package com.stackroute.keepnote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	/* Here we define a custom query to fetch user based on username and password. After findBy we specify the variable names 
	as present in User.java */
	public User findByUserIdAndUserPassword(String userId, String userPassword);
	
	public User findByUserName(String userName);

}
