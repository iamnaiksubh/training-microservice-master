package com.dnb.customerservice.exception;

public class InvalidFloatException extends Exception {
	
	public InvalidFloatException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " " + getMessage();
	}
}
