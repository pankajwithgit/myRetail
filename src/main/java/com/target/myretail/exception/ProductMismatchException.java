package com.target.myretail.exception;

public class ProductMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProductMismatchException(String message) {
		this(message, null);
	}
	
	public ProductMismatchException(String message, Throwable cause) {
		super(message, cause);
	}
}

