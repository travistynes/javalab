package com.mes.payouts.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;

/**
 * Configure multiple security instances (basic auth and form login).
 * See: https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#multiple-httpsecurity
 *
 * Configure Spring Security with Vaadin.
 * See: https://vaadin.com/tutorials/securing-your-app-with-spring-security/setting-up-spring-security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	/**
	 * This security configuration will be considered first because it has an
	 * @Order value of 1. It will only be applicable to URLs that start with /api/v1/.
	 */
	@Configuration
	@Order(1)
	public static class BasicAuthConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		private AuthProvider authProvider;

		@Override
		protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
			authBuilder.authenticationProvider(authProvider);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// This HttpSecurity will only be applicable to URLs that start with /api/v1/
			http.antMatcher("/api/v1/**")
				.authorizeRequests()

				// Authenticate all requests
				.anyRequest().authenticated()

				// Setup basic authentication
				.and().httpBasic()

				// Every request must be re-authenticated (per REST stateless constraint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}

	/**
	 * This security configuration will be used for URLs that don't start with /api/.
	 * This configuration is considered after the BasicAuthConfiguration since it
	 * has an @Order value after 1 (no @Order defaults to last).
	 */
	@Configuration
	public static class FormLoginConfiguration extends WebSecurityConfigurerAdapter {
		@Autowired
		AuthenticationSuccessHandler authSuccessHandler;

		@Value("${server.port}")
		private String port;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Not using Spring CSRF. Vaadin has built-in CSRF.
			http.csrf().disable()
				// Redirect unauthenticated requests to login page.
				.exceptionHandling().authenticationEntryPoint(loginUrlEntryPoint())

				// Setup access restrictions. Matchers are considered in order.
				.and().authorizeRequests()

				// Public resources
				.and().authorizeRequests().antMatchers("/actuator/**", "/public/**", "/login").permitAll()

				// Allow all Vaadin Flow internal requests
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Authenticate all other requests
				.anyRequest().authenticated();

				/**
				 * Setup form login authentication. Unauthenticated users
				 * will be redirected to the loginPage. Upon successful authentication,
				 * they will be redirected to the originally requested page.
				 */
				/*
				.and().formLogin().permitAll()
				.loginPage("/login")
				.failureUrl("/login")
				.successHandler(authSuccessHandler)

				// Configure logout. The default logout URL in Spring is "/logout"
				.and().logout()
				.logoutSuccessUrl("/login");
				*/

			/**
			 * Spring Security seems to redirect https requests to port 8443 by default,
			 * but not always (it always happens during a failed login attempt).
			 * This will map it back to the port we have configured for the application.
			 */
			//http.portMapper().http(8443).mapsTo(Integer.parseInt(port));

			// Allow frames in the page. For example, h2-console won't work without this.
			http.headers().frameOptions().disable();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers(
					"/VAADIN/**",
					"/favicon.ico",
					"/robots.txt",
					"/manifest.webmanifest",
					"/sw.js",
					"/offline-page.html",
					"/frontend/**",
					"/webjars/**",
					"/frontend-es5/**",
					"/frontend-es6/**"
					);
		}

		@Bean
		public AuthenticationEntryPoint loginUrlEntryPoint() {
			return new LoginUrlAuthenticationEntryPoint("/login");
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
