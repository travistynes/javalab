package com.mes.payouts.ui.login;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.login.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Access this page at: https://localhost:8081/javalab/login
 *
 * Vaadin API doc: https://vaadin.com/api/platform/14.0.4/overview-summary.html
 *
 * See all Vaadin components (checkbox, text field, date picker, etc) at
 * https://vaadin.com/components
 */
@Route(value="login")
@StyleSheet("styles/styles.css")
public class LoginPage extends VerticalLayout {
	private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

	private LoginForm loginForm = new LoginForm();

	@Autowired
	private AuthenticationManager authManager;

	public LoginPage() {
		this.createUI();
	}

	private void createUI() {
		HorizontalLayout header = new HorizontalLayout();
		HorizontalLayout content = new HorizontalLayout();
		HorizontalLayout footer = new HorizontalLayout();

		// Configure layouts
		this.setSizeFull();
		this.setPadding(true);
		this.setSpacing(true);

		header.setWidth("100%");
		header.setPadding(true);
		header.setJustifyContentMode(JustifyContentMode.CENTER);

		content.setWidth("100%");
		content.setFlexGrow(1, content);
		content.setAlignItems(Alignment.CENTER); // Vertical alignment
		content.setJustifyContentMode(JustifyContentMode.CENTER); // Horizontal alignment

		footer.setWidth("100%");
		footer.setPadding(true);
		footer.setAlignItems(Alignment.CENTER);
		footer.add(new H3("Footer"));
		
		/**
		 * Add login form.
		 * To set a custom error message, see setI18n(LoginI18n).
		 * https://vaadin.com/api/platform/14.0.5/com/vaadin/flow/component/login/LoginForm.html
		 */
		//loginForm.addClassName("login-border");
		loginForm.setForgotPasswordButtonVisible(false);
		content.getElement().appendChild(loginForm.getElement());

		// Login submit listener
		loginForm.addLoginListener(event -> login(event));

		// Compose layout
		this.add(header, content, footer);
		//this.expand(center);
	}

	private void login(LoginEvent event) {
		String username = event.getUsername();
		String password = event.getPassword();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

		// Set authentication in Spring Security
		try {
			Authentication auth = authManager.authenticate(authToken);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);

			// Redirect authenticated user
			UI.getCurrent().navigate(com.mes.payouts.controllers.MerchantSignupPage.class);
		} catch(BadCredentialsException e) {
			loginForm.setError(true);
		}
	}
}
