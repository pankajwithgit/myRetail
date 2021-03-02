package com.target.myretail;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.target.myretail.controller.ProductController;

@SpringBootTest
class MyRetailApplicationTests {

	@Autowired
	private ProductController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
