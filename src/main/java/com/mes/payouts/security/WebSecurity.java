package com.mes.payouts.security;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Configure Spring Security with Vaadin.
 * See: https://vaadin.com/tutorials/securing-your-app-with-spring-security/setting-up-spring-security
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.authenticationProvider(authProvider);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			// Not using Spring CSRF. Vaadin has built-in CSRF.
			http.csrf().disable()

			// Setup access restrictions. Matchers are considered in order.
			.authorizeRequests()

			// Public resources (example)
			.and().authorizeRequests().antMatchers("/v1/public/**", "/guest/**").permitAll()

			// Authenticate all requests
			.anyRequest().authenticated()

			// Setup login authentication
			.and().formLogin()
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/login/process")
			.failureUrl("/login?error")
			.defaultSuccessUrl("/login/success")

			// Basic authentication
			.and().httpBasic()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
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
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
