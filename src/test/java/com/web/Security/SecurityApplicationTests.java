package com.web.Security;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Value("${spring.security.jwt.secret.key}")
	private String jwtSecretKey;

	@Test
	@PostConstruct
	void contextLoads() {

		System.out.println("JWT Secret Key: " + jwtSecretKey);

	}

}
