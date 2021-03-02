package com.target.myretail.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(Long productId) {
		this(String.format("Product with id: %s not found in system.", productId));
	}

	public ProductNotFoundException(String message) {
		this(message, null);
	}

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
