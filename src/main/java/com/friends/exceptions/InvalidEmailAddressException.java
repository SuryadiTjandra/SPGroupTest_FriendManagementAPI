package com.friends.exceptions;

public class InvalidEmailAddressException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param email the email which is not valid
	 */
	public InvalidEmailAddressException(String email){
		super("Invalid email address: '" + email + "' is not a valid email address.");
	}
}
