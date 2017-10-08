package com.friends.exceptions;

public class AlreadyBlockedException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public AlreadyBlockedException(String requestor, String target) {
		super(requestor + " has already blocked updates from " + target);
	}

	@Override
	public String getErrorCode() {
		return "300";
	}

}
