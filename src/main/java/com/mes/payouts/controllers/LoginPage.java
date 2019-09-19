package com.mes.payouts.controllers;

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
import java.time.LocalDate;
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
@StyleSheet("styles.css")
public class LoginPage extends VerticalLayout {
	private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

	private LoginForm loginForm = new LoginForm();

	public LoginPage() {
		this.createUI();
	}

	private void createUI() {
		loginForm.setAction("login");
		this.getElement().appendChild(loginForm.getElement());
	}
}
