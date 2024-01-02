package com.dnb.customerservice.exception;

public class IdNotFoundException extends Exception{
	
	public IdNotFoundException(String msg) {
		super(msg);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + getMessage();
	}
}
