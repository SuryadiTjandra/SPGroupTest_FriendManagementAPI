package com.friends.exceptions;

public class UserNotFoundException extends ApplicationException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param user the email address of the user which could not be found
	 */
	public UserNotFoundException(String user){
		super("User not found: User " + user + " does not exist");
	}
}
