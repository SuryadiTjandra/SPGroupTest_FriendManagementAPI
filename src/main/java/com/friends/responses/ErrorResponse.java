package com.friends.responses;

public class ErrorResponse extends Response{

	private String message;
	
	public ErrorResponse(String message) {
		super(false);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
