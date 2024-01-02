package com.dnb.accountservice.exception;

public class InvalidNameException extends Exception {

	public InvalidNameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + super.getMessage();
	}

}
