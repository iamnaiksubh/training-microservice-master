package com.dnb.customerservice.exception;

public class InvalidDateException extends Exception {
	
	public InvalidDateException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " " + super.getMessage();
	}
}
