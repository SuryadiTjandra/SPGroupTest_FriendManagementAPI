package com.friends.exceptions;

public class WrongRequestFormatException extends ApplicationException{

	private static final long serialVersionUID = 1L;

	public WrongRequestFormatException(String message){
		super("Wrong request format : " + message);
	}

	@Override
	public String getErrorCode() {
		return "000";
	}
}
