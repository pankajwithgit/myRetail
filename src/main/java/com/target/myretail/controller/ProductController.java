package com.target.myretail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.target.myretail.exception.ProductMismatchException;
import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.model.Product;
import com.target.myretail.service.ProductService;

/**
 * The rest controller for /products API end points.
 * 
 * @author Pankaj K
 */
@RestController
@RequestMapping(value = "api/v1/products")
public class ProductController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") long productId) {
		logger.info("inside getProductDetails " + productId);
		
		Product product = null;
		try {
			product = productService.getProduct(productId);
		} catch (ProductNotFoundException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(
			@RequestBody Product product,
			@PathVariable(value = "id") long productId) {
		logger.debug("inside updateProduct " + productId);
		
		Product updatedProduct = null;

		if (!product.getId().equals(productId)) {
			throw new ProductMismatchException("Product id in url and body doesn't match.");
		}

		try {
			updatedProduct = productService.updateProduct(productId, product);
		} catch (ProductNotFoundException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
	}
}
