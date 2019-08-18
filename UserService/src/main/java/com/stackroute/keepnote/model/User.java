package com.stackroute.keepnote.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class User {

	/**
	 * 
	 */
	@Id
	@Field("userId")
	private String userId;
	/**
	 * 
	 */
	private String userName;
	/**
	 * 
	 */
	private String userPassword;
	/**
	 * 
	 */
	private String userMobile;
	/**
	 * 
	 */
	private Date userAddedDate;

	/**
	 * 
	 */
	public User() {
		super();
	}

	/**
	 * 
	 * @param userId
	 * @param userName
	 * @param userPassword
	 * @param userMobile
	 * @param userAddedDate
	 */
	public User(String userId, String userName, String userPassword, String userMobile, Date userAddedDate) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
		this.userAddedDate = userAddedDate;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(final String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * @param userMobile
	 *            the userMobile to set
	 */
	public void setUserMobile(final String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the userAddedDate
	 */
	public Date getUserAddedDate() {
		return userAddedDate;
	}

	/**
	 * @param userAddedDate
	 *            the userAddedDate to set
	 */
	public void setUserAddedDate(final Date userAddedDate) {
		this.userAddedDate = userAddedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userMobile="
				+ userMobile + ", userAddedDate=" + userAddedDate + "]";
	}
}
