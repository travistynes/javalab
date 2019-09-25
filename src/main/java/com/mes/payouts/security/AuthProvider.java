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
import org.springframework.security.core.userdetails.UserDetails;
import com.mes.payouts.security.user.MesUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

@Component
public class AuthProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(AuthProvider.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MesUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String name = authentication.getName();
		String credentials = authentication.getCredentials().toString();

		if (StringUtils.isBlank(credentials)) {
			throw new BadCredentialsException("Missing password.");
		}

		UserDetails user = userDetailsService.loadUserByUsername(name);

		if(name.equals(user.getUsername()) && credentials.equals(user.getPassword()) && user.isEnabled()) {
			log.debug("Authenticated: " + name);

			// Return a trusted (isAuthenticated() == true) token.
			Authentication auth = new UsernamePasswordAuthenticationToken(name, credentials, new ArrayList<>());

			return auth;
		} else {
			log.debug("Authentication failed: " + name);

			throw new BadCredentialsException("Incorrect username or password.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
