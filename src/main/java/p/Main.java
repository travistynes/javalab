package p;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.SessionTrackingMode;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		log.info("Application starting.");
		SpringApplication.run(Main.class, args);

		log.info("Application started.");
	}

	/**
	 * Called after container has constructed the Main instance.
	 */
	@PostConstruct
	private void init() {
		log.info("Application created. Running post initialization.");

		AuthenticationManager man = (auth -> {
			return auth;
		});

		Authentication req = new UsernamePasswordAuthenticationToken("user", "pass", null);
		Authentication res = man.authenticate(req);
		SecurityContextHolder.getContext().setAuthentication(res);
		log.info("Auth: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

		log.info("Initialization complete.");
	}

	/**
	 * Shutdown hook.
	 */
	@PreDestroy
	private void shutdown() {
		log.info("Application shutdown.");
	}

	@Configuration
	@EnableWebSecurity
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
					.anyRequest().authenticated()
                    .and().logout()
					.and().httpBasic();
		}

		@Bean
		@Override
		public UserDetailsService userDetailsService() {
			UserDetails user = User.withDefaultPasswordEncoder().username("bob").password("bob").roles("USER").build();
			Collection<UserDetails> users = List.of(user);
			InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(users);

			return manager;
		}
	}
}
