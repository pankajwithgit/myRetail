package com.target.myretail.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.repository.ProductRepository;

/**
 * Responsible to initialize mongo database. Refreshes the collection of
 * products at application start.
 * 
 * @author Pankaj K
 */
@Service
public class DbSeederService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductRepository productRepository;

	@PostConstruct
	public void init() {
		addProducts();
	}

	private void addProducts() {
		logger.debug("Adding initial set of documents");

		List<Product> products = new ArrayList<>();

		Product product1 = new Product(13860428L, new ProductPrice(13.49, "USD"));
		products.add(product1);

		Product product2 = new Product(54456119L, new ProductPrice(19.51, "USD"));
		products.add(product2);

		Product product3 = new Product(13264003L, new ProductPrice(103.49, "USD"));
		products.add(product3);

		Product product4 = new Product(12954218L, new ProductPrice(56.13, "USD"));
		products.add(product4);

		// Clear existing documents first.
		productRepository.deleteAll();

		// Add some documents to begin with.
		productRepository.saveAll(products);

	}
}
