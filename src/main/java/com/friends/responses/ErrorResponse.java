package com.friends.responses;

public class ErrorResponse extends Response{

	private String errorCode;
	private String message;
	
	public ErrorResponse(String errorCode, String message) {
		super(false);
		this.setErrorCode(errorCode);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
