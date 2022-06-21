package org.akj.springboot.authorization.config;

import java.net.MalformedURLException;
import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.ResourceUtils;

@Configuration
@RefreshScope
public class JwtTokenConfiguration {

	@Value("${security.oauth2.jwt.key-pair.store-pass}")
	private String keyStorePass;

	@Value("${security.oauth2.jwt.key-pair.alias}")
	private String keyPairAlias;

	@Value("${security.oauth2.jwt.key-pair.file-path}")
	private String keyStoreFilePath;

	@Bean
	@Primary
	public TokenStore jwtTokenStore() throws MalformedURLException {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * token generation: signature
	 * @throws MalformedURLException 
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() throws MalformedURLException {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		KeyPair keyPair = null;
		
		if(keyStoreFilePath.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			keyPair = new KeyStoreKeyFactory(new FileUrlResource(keyStoreFilePath), keyStorePass.toCharArray())
					.getKeyPair(keyPairAlias);
		}else {
			keyPair = new KeyStoreKeyFactory(new ClassPathResource(keyStoreFilePath), keyStorePass.toCharArray())
					.getKeyPair(keyPairAlias);
		}

		accessTokenConverter.setKeyPair(keyPair);
		
		return accessTokenConverter;
	}
}