package com.mes.payouts.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

	@Value("${security.authToken}")
	private String authToken;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String password = authentication.getCredentials().toString();

		if (StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Missing auth token.");
		}

		if(password.equals(authToken)) {
			return authentication;
		}

		throw new BadCredentialsException("Invalid auth token.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
