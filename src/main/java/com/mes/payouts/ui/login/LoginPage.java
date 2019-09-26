package com.mes.payouts.ui.login;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.login.LoginForm;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
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
@UIScope
@StyleSheet("styles/styles.css")
public class LoginPage extends VerticalLayout {
	private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private AuthenticationManager authManager;

	private VerticalLayout content;
	private LoginForm loginForm = new LoginForm();
	private Icon loginSuccessIcon = new Icon(VaadinIcon.CHECK_CIRCLE);

	public LoginPage() {
		this.createUI();
	}

	@PostConstruct
	private void init() {

	}

	private void createUI() {
		HorizontalLayout header = new HorizontalLayout();
		content = new VerticalLayout();
		HorizontalLayout footer = new HorizontalLayout();

		// Configure layouts
		this.setSizeFull();
		this.setPadding(false);
		this.setSpacing(true);

		header.setWidth("100%");
		header.setPadding(true);
		header.setJustifyContentMode(JustifyContentMode.CENTER);
		header.addClassName("header");
		
		Image headerLogo = new Image("frontend/images/logo_black_transparent.png", "Merchant e-Solutions");
		headerLogo.addClassName("header-logo");
		header.add(headerLogo);

		/**
		 * See flex box alignment:
		 * https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Flexible_Box_Layout/Aligning_Items_in_a_Flex_Container
		 */
		content.setWidth("100%");
		content.setAlignItems(Alignment.CENTER); // Cross axis
		content.setJustifyContentMode(JustifyContentMode.EVENLY); // Main axis

		Paragraph p1 = new Paragraph("Login to opt-in to the Faster Funding and Immediate Pre-Funding program.");

		/**
		 * Add login form.
		 * To set a custom error message, see setI18n(LoginI18n).
		 * https://vaadin.com/api/platform/14.0.5/com/vaadin/flow/component/login/LoginForm.html
		 */
		loginForm.setForgotPasswordButtonVisible(false);

		// Login submit listener
		loginForm.addLoginListener(event -> login(event));

		footer.setWidth("100%");
		footer.setPadding(true);
		footer.setSpacing(false);
		footer.addClassName("footer");
		footer.setAlignItems(Alignment.CENTER);
		footer.setJustifyContentMode(JustifyContentMode.EVENLY);

		Image footerMesLogo = new Image("frontend/images/logo_white.png", "Merchant e-Solutions");
		footerMesLogo.addClassName("footer-mes-logo");

		Image partnerIcon = new Image("frontend/images/partner_icon.png", "Technology Partner");
		partnerIcon.addClassName("partner-icon");

		Image footerPayoutsNetworkLogo = new Image("frontend/images/logo_payoutsnetwork.png", "Payouts Network");
		footerPayoutsNetworkLogo.addClassName("footer-pon-logo");

		footer.add(footerMesLogo, partnerIcon, footerPayoutsNetworkLogo);
		
		// Compose layout
		content.add(p1, loginForm);
		this.add(header, content, footer);

		//this.expand(content);
		//content.setFlexGrow(1, p1);
		this.setFlexGrow(1, content);
	}

	/**
	 * This is how to manually authenticate to Spring Security.
	 * This isn't being used for now because formLogin() is enabled in SecurityConfiguration
	 * so Spring itself will process the authentication when the user logs in.
	 */
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
			String attr = "SPRING_SECURITY_SAVED_REQUEST";
			DefaultSavedRequest savedRequest = (DefaultSavedRequest)request.getSession().getAttribute(attr);
			
			if(savedRequest != null) {
				// Redirect to originally requested page.
				String contextPath = savedRequest.getContextPath();
				String targetUri = StringUtils.stripStart(savedRequest.getRequestURI(), contextPath);

				UI.getCurrent().getPage().setLocation(savedRequest.getRequestURI());
			} else {
				// User didn't have an original destination (directly hit login page).
				//UI.getCurrent().navigate(LoginSuccess.class);
				content.add(loginSuccessIcon);
				loginForm.setEnabled(true);
			}
		} catch(BadCredentialsException e) {
			loginForm.setError(true);

			content.remove(loginSuccessIcon);
		}
	}
}
