package com.friends.exceptions;

public class AlreadyFriendsException extends ApplicationException{
	private static final long serialVersionUID = 1L;

	public AlreadyFriendsException(String user1, String user2) {
		super("Could not make a friend connection. " + user1 + " and " + user2 + " are already friends.");
	}

	@Override
	public String getErrorCode() {
		return "100";
	}
}
