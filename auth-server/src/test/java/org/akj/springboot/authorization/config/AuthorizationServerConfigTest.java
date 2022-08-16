package org.akj.springboot.authorization.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthorizationServerConfigurationTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		String pass = "123456";
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashPass = bcryptPasswordEncoder.encode(pass);
		System.out.println(hashPass);

		boolean f = bcryptPasswordEncoder.matches("123456", hashPass);
		Assertions.assertTrue(f);
	}

}
