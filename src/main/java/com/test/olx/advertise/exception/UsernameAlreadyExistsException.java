package com.test.olx.advertise.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message = "";
	public UsernameAlreadyExistsException() {}
	public UsernameAlreadyExistsException(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Username Already Exists " + message;
	}
	
}
