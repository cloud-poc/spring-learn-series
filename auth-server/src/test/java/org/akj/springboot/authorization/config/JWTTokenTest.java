package org.akj.springboot.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.util.UUID;

@Slf4j
class JWTTokenTest {

	@Test
	void test() {
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTI1Nzg2MzMsInVzZXJfbmFtZSI6ImphbWllIiwiYXV0aG9yaXRpZXMiOlsiMDIxMDk4IiwiMDIxMDQwIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI3N2RkOTYzZS03OGUzLTQyMzUtYmU0ZC0xYzBhZGYyOGIzOGIiLCJjbGllbnRfaWQiOiJjbGllbnRfMiIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdfQ.g98Nb7shLnZJjqamwz2Kb6ZOduizoEJxvqP21zJR1FGgQqWzuJfLUp4XFhWC3wU_sqxaNGcxLPsEvcb_UyortzmLjw_bDbP9o5KDhOO03h3e_dm76YmnS0Ug7Dc5Z31_k6g1tamTn9lvI8Bug87UZDY1q3FoSZYDj2hvFhJs-GPEg2XH0nB3c3UMUNrszG0NfciLW7UiDRNt2rMRDKuOsBJGDG4kr75KAOXkRCPGv_AId0PHOljGOL2qXkfxMLSg4GPXBUzvMYv3EoFJMlHtF-uyxaQ3lk8VsA4x0KsNG1U_urcj4NNRhsUX9Cfp0S2gHYIqxTlhBUM95X8AFS2q-A";

		Jwt jwt = JwtHelper.decode(token);

		System.out.println(jwt.toString());
		System.out.println(jwt.getClaims());
		System.out.println(jwt.getEncoded());

		String publicKey = "-----BEGIN PUBLIC KEY-----\n"
				+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApojM1Pvo98uvIk+2VTtGWdbdpGRD04ooIU/BJze6wmn1vEc/L8A109ZXoAxg5JEK3ek40TzKtdWJGUBbPoIUc3Ko0gcDMQiqtt+R0HlwtPltp8d2Dhb5VdECSyE96pnTFpUtF5GXZoQAZ6X/xWqft+iYmhozNxOzrHEWTVRq0sQ4ba3YvPoH6dUob0kUohGlUk8yOhf5ELSrlK0yYGkfWGjX2UhJuNeRwn/aSJcYK/P+nj0TfUFL15c96IXWH3LBvVjYsimzVRam5dAePOGflrDVVstaeuwksQyCCCujynS4qWfpPKtp5FSiykXgapnkdWmOG5k7SjhjFBNvgzbTGwIDAQAB\n"
				+ "-----END PUBLIC KEY-----";

		Jwt jwt1 = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));

		Assertions.assertEquals(jwt.toString(), jwt1.toString());

	}

	@Test
	public void test1(){
		log.info(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
	}

}
