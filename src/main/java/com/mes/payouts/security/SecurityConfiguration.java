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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
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
		@Value("${server.port}")
		private String port;

		@Bean
		@Override
		public UserDetailsService userDetailsService() {
			UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password123").roles("USER").build();

			return new InMemoryUserDetailsManager(user);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Not using Spring CSRF. Vaadin has built-in CSRF.
			http.csrf().disable()

				// Setup access restrictions. Matchers are considered in order.
				.authorizeRequests()

				// Public resources (example)
				.and().authorizeRequests().antMatchers("/actuator/**", "/public/**", "/login").permitAll()

				// Allow all Vaadin Flow internal requests
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Authenticate all other requests
				.anyRequest().authenticated();

				/**
				 * Setup form login authentication. Unauthenticated users
				 * will be redirected to the loginPage. Upon successful authentication,
				 * they will be redirected to the originally requested page.
				 * If a page wasn't originally requested (they hit logingPage directly)
				 * then the defaultSuccessUrl will be used.
				 */
				/*
				.and().formLogin().permitAll()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.failureUrl("/login/error")
				.defaultSuccessUrl("/vaadin")

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
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
