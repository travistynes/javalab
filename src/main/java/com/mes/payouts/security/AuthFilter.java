package com.mes.payouts.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

	@Autowired
	private AuthenticationManager authManager;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String credential = request.getHeader("X-AUTH-TOKEN");
		credential = credential == null ? request.getParameter("X-AUTH-TOKEN") : credential;

		if(credential != null) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("user", credential, null);
			try {
				Authentication auth = authManager.authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch(BadCredentialsException e) {
				log.error("Bad credentials: " + e.getMessage() + " Request url: " + request.getRequestURL());
			}
		}	

		chain.doFilter(request, response);
	}
}
