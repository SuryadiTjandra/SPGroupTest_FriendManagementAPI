package com.friends.exceptions;

public class SameUserException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public SameUserException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "003";
	}

}
