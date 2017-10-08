package com.friends.exceptions;

public class UserBlockedException extends ApplicationException{

	private static final long serialVersionUID = 1L;

	public UserBlockedException(String blocker, String blockee) {
		super("Could not make a friend connection. " + blocker + " has blocked " + blockee);
	}

	@Override
	public String getErrorCode() {
		return "101";
	}
	
}
