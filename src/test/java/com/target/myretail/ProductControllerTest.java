package com.target.myretail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.controller.ProductController;
import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.service.ProductService;

@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getProduct_success() throws Exception {
		Product mockProduct = new Product(12954218L, "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
				new ProductPrice(56.13, "USD"));
		Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(mockProduct);

		String relativeUrl = "/api/v1/products/12954218";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(relativeUrl)
				.accept(MediaType.APPLICATION_JSON_VALUE);

		// Actual Result
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// Expected Result
		String expectedProductJson = "{\"id\": 12954218,\"name\": \"Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz\",\"current_price\": {\"value\": 56.13,\"currency_code\": \"USD\"}}";

		JSONAssert.assertEquals(expectedProductJson, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getProduct_failure() throws Exception {
		Mockito.when(productService.getProduct(Mockito.anyLong())).thenThrow(new ProductNotFoundException(45L));

		String relativeUrl = "/api/v1/products/45";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(relativeUrl)
				.accept(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(requestBuilder)
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals("404 NOT_FOUND \"Product with id: 45 not found in system.\"",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void updateProduct_success() throws Exception {
		Product mockProduct = new Product(12954218L, "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
				new ProductPrice(56.13, "USD"));
		Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any())).thenReturn(mockProduct);

		String relativeUrl = "/api/v1/products/12954218";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(relativeUrl)
				.content(asJsonString(mockProduct))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_VALUE);

		// Actual Result
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// Expected Result
		String expectedProductJson = "{\"id\": 12954218,\"name\": \"Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz\",\"current_price\": {\"value\": 56.13,\"currency_code\": \"USD\"}}";

		JSONAssert.assertEquals(expectedProductJson, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void updateProduct_failure() throws Exception {
		Product mockProduct = new Product(12954218L, "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
				new ProductPrice(56.13, "USD"));
		Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any()))
				.thenThrow(new ProductNotFoundException(45L));

		String relativeUrl = "/api/v1/products/12954218";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(relativeUrl)
				.content(asJsonString(mockProduct))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_VALUE);

		mockMvc.perform(requestBuilder)
				.andExpect(status()
				.isNotFound())
				.andExpect(result -> assertEquals("404 NOT_FOUND \"Product with id: 45 not found in system.\"",
						result.getResolvedException().getMessage()));
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
