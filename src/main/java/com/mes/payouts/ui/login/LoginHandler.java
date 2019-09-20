package com.mes.payouts.ui.login;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginHandler {
	private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);
	
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html";

	@PostMapping("/login/process")
	public void handleLogin(@RequestParam(value="username") String username, @RequestParam(value="password") String password, HttpServletResponse response) throws IOException {
		try {
			log.info("Login: " + username);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		catch (Exception e) {
			log.error("Login error.", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
		}
	}
}
