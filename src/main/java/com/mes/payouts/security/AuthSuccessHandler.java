package com.mes.payouts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This would run if form login was enabled in Spring Security. Since I'm manually
 * authenticating to Spring Security, this won't run.
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler{
	private static final Logger log = LoggerFactory.getLogger(AuthSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		String name = userDetails.getUsername();

		log.debug("Authenticated: " + name);
	}
}
