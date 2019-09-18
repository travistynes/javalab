package com.mes.payouts.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayoutsController {
	private static final Logger log = LoggerFactory.getLogger(PayoutsController.class);
	
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html";

	@GetMapping("/api/v1/{resource}")
	public void getSecureResource(@PathVariable String resource, HttpServletResponse response) throws IOException {
		try {
			log.info("Requested API resource: " + resource);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		catch (Exception e) {
			log.error("Error accessing resource (" + resource + ").", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
		}
	}

	@GetMapping("/public/{resource}")
	public void getPublicResource(@PathVariable String resource, HttpServletResponse response) throws IOException {
		try {
			log.info("Requested public resource: " + resource);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		catch (Exception e) {
			log.error("Error accessing resource (" + resource + ").", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
		}
	}
}
