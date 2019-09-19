package com.mes.payouts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

@Component
public class AuthProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(AuthProvider.class);

	@Value("${security.user}")
	private String user;

	@Value("${security.password}")
	private String password;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String name = authentication.getName();
		String credentials = authentication.getCredentials().toString();

		if (StringUtils.isBlank(credentials)) {
			throw new BadCredentialsException("Missing password.");
		}

		if(name.equals(user) && credentials.equals(password)) {
			// Return a trusted (isAuthenticated() == true) token.
			log.debug("Authenticated: " + name);

			return new UsernamePasswordAuthenticationToken(name, credentials, new ArrayList<>());
		}

		log.debug("Authentication failed: " + name);

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
