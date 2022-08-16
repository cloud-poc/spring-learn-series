package org.akj.springboot.authorization.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/profile/clients")
public class ClientDetailsController {

    private final JdbcClientDetailsService jdbcClientDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${security.oauth2.client.credential-length: 8}")
    private Integer credentialLength;
    @Value("${security.oauth2.client.id-length: 16}")
    private Integer clientIdLength;

    public ClientDetailsController(JdbcClientDetailsService jdbcClientDetailsService,
                                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcClientDetailsService = jdbcClientDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * endpoint for client application registration
     *
     * @param clientDetails
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin') or hasAnyAuthority('editor')")
    public ClientDetails register(@RequestBody BaseClientDetails clientDetails) {

        String clientId = UUID.randomUUID().toString().replace("-", "");
        String clientSecret = base64StringGenerator(credentialLength);
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(bCryptPasswordEncoder.encode(clientSecret));
        clientDetails.setAuthorizedGrantTypes(List.of("password", "implicit"));

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
