package com.friends.exceptions;

public class WrongRequestFormatException extends ApplicationException{

	private static final long serialVersionUID = 1L;

	public WrongRequestFormatException(String message){
		super(message);
	}
}
