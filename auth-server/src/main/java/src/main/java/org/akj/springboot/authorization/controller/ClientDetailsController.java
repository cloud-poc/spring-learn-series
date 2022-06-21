package org.akj.springboot.authorization.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientDetailsController {

	@Autowired
	private JdbcClientDetailsService jdbcClientDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * endpoint for client application registration
	 * @param clientDetails
	 * @return
	 */
	@RequestMapping(value = "/profile/clients", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ClientDetails register(@RequestBody BaseClientDetails clientDetails) {

		String clientId = UUID.randomUUID().toString().replace("-", "");
		String clientSecret = base64StringGenerator(32);
		clientDetails.setClientId(clientId);
		clientDetails.setClientSecret(bCryptPasswordEncoder.encode(clientSecret));

		jdbcClientDetailsService.addClientDetails(clientDetails);
		
		//reset to plain client secret
		clientDetails.setClientSecret(clientSecret);
		
		return clientDetails;
	}

	private String base64StringGenerator(int length) {
		RandomValueStringGenerator randomValueStringGenerator = new RandomValueStringGenerator();
		randomValueStringGenerator.setLength(length);

		return randomValueStringGenerator.generate();
	}
}
