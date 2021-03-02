package com.target.myretail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.target.myretail.model.Product;

/**
 * Mongo repo for Product.
 * 
 * @author Pankaj K
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
}
