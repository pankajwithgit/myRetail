package com.target.myretail.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class for Product.
 * 
 * @author Pankaj K
 */
@Document(collection = "products")
public class Product {

	@Id
	private Long id;

	@Transient
	private String name;

	private ProductPrice current_price;

	// default constructor
	public Product() {
	}

	public Product(Long id, ProductPrice current_price) {
		this.id = id;
		this.current_price = current_price;
	}
	
	public Product(Long id, String name, ProductPrice current_price) {
		this.id = id;
		this.name = name;
		this.current_price = current_price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductPrice getCurrent_price() {
		return current_price;
	}

	public void setCurrent_price(ProductPrice current_price) {
		this.current_price = current_price;
	}
}
