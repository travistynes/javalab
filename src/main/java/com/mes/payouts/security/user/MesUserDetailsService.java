package com.mes.payouts.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

@Service
public class MesUserDetailsService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(MesUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByLoginName(username);

		if(user == null) {
			throw new BadCredentialsException("User not found: " + username);
		}

		return new MesUserDetails(user);
	}
}
