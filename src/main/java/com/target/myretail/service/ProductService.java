package com.target.myretail.service;

import java.util.concurrent.ExecutionException;

import com.target.myretail.model.Product;

/**
 * A contract for Product Service.
 * 
 * @author Pankaj k
 */
public interface ProductService {

	public Product getProduct(Long id) throws InterruptedException, ExecutionException ;
	
	public Product updateProduct(Long id, Product mutatedProduct);
}
