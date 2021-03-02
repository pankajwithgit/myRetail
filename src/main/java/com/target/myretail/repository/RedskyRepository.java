package com.target.myretail.repository;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.exception.ProductNotFoundException;

/**
 * Repo for Redsky. Pretending it to be an internal resource.
 * 
 * @author Pankaj K
 */
@Repository
public class RedskyRepository {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String REDSKY_URL = "https://redsky.target.com/v3/pdp/tcin/%s?"
			+ "excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,"
			+ "rating_and_review_statistics,question_answer_statistics&key=candidate#_blank";

	public String getProductNameById(long id) {
		logger.debug(String.format("Fetching product name with id: %s", id));

		RestTemplate restTemplate = new RestTemplate();

		ObjectMapper mapper = new ObjectMapper();

		String productUrl = String.format(REDSKY_URL, id);

		try {
			// http get from redsky
			ResponseEntity<String> response = restTemplate.getForEntity(productUrl, String.class);
			Map<String, Map> info = mapper.readValue(response.getBody(), Map.class);

			// need to go deep in json response to find product title
			Map<String, Map> productMap = info.get("product");
			Map<String, Map> itemMap = productMap.get("item");
			Map<String, String> prodDescMap = itemMap.get("product_description");
			
			return prodDescMap.get("title");
		} catch (Exception e) { 
			throw new ProductNotFoundException(id);
		}

	}
}
