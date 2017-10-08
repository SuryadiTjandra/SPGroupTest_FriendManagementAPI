package com.friends.exceptions;

public class AlreadySubscribedException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public AlreadySubscribedException(String requestor, String target) {
		super(requestor + " has already subscribed to " + target);
	}

	@Override
	public String getErrorCode() {
		return "200";
	}

}
