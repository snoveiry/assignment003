package com.noveiry.assignment;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void homeMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:8080",
				String.class)).contains("Hello, Frontend!");
	}
    
}
