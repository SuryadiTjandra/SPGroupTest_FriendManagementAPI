package com.friends.exceptions;

/**
 * 
 * @author user
 * A runtime exception which is the base for all exceptions expected within the application itself.
 * All its subclasses are expected to have messages relevant to what errors they represent.
 */
public abstract class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ApplicationException(String message){
		super(message);
	}
}
