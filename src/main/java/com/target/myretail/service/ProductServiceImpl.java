package com.target.myretail.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.model.Product;
import com.target.myretail.repository.ProductRepository;
import com.target.myretail.repository.RedskyRepository;

/**
 * Responsible for anything related to product.
 * 
 * @author Pankaj K
 */
@Service
public class ProductServiceImpl implements ProductService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("fixedThreadPool")
	private ExecutorService executorService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private RedskyRepository redskyRepository;

	@Override
	public Product getProduct(Long id) throws InterruptedException, ExecutionException {
		logger.info(String.format("Fetching product details with id: %s", id));

		Future<String> nameFuture = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return redskyRepository.getProductNameById(id);
			}
		});

		Future<Product> pricingInfoFuture = executorService.submit(new Callable<Product>() {
			@Override
			public Product call() throws Exception {
				return productRepository.findById(id).orElse(null);
			}
		});

		if (pricingInfoFuture.get() == null) {
			throw new ProductNotFoundException(id);
		}
		return new Product(id, nameFuture.get(), pricingInfoFuture.get().getCurrent_price());
	}

	@Override
	public Product updateProduct(Long id, Product mutatedProduct) {
		logger.debug(String.format("Updating product details with id: %s", id));

		if (!productRepository.existsById(id)) {
			throw new ProductNotFoundException(id);
		}
		return productRepository.save(mutatedProduct);
	}

}
