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
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value="login/success")
@StyleSheet("styles/styles.css")
public class LoginSuccess extends VerticalLayout {
	private static final Logger log = LoggerFactory.getLogger(LoginSuccess.class);

	public LoginSuccess() {
		this.createUI();
		this.greet();
	}

	private void createUI() {
		HorizontalLayout header = new HorizontalLayout();
		HorizontalLayout center = new HorizontalLayout();
		VerticalLayout optionsPanel = new VerticalLayout();
		VerticalLayout content = new VerticalLayout();
		HorizontalLayout footer = new HorizontalLayout();

		// Configure layouts
		this.setSizeFull();
		this.setPadding(true);
		this.setSpacing(true);

		header.setWidth("100%");
		header.setPadding(true);
		header.setJustifyContentMode(JustifyContentMode.CENTER);
		header.add(new H2("Login - Success"));

		optionsPanel.setWidth("400px");
		optionsPanel.setJustifyContentMode(JustifyContentMode.START);
		optionsPanel.addClassName("left-border");
		optionsPanel.add(new H3("Products"));

		center.setWidth("100%");
		center.setFlexGrow(1, content);

		content.setAlignItems(Alignment.STRETCH);
		content.setWidth("100%");
		content.add(new H3("Terms and Conditions"));

		Div tc = new Div();
		tc.setText("The following terms & conditions apply.");
		content.add(tc);
		
		// Checkboxes: https://vaadin.com/components/vaadin-checkbox/java-examples
		Checkbox terms = new Checkbox("Accept Terms & Conditions");
		Checkbox prefunding = new Checkbox("Prefunding");
		Checkbox fasterFunding = new Checkbox("Faster Funding");

		// Date picker
		DatePicker startDatePicker = new DatePicker();
		startDatePicker.setLabel("Select start date");
		startDatePicker.setPlaceholder("Start date");

		startDatePicker.addValueChangeListener(e -> {
			LocalDate startDate = e.getValue();
			log.info("Selected start date: " + startDate.toString());
		});

		// Submit button
		Button submitButton = new Button("Submit", VaadinIcon.CHECK.create());

		terms.setValue(false);

		prefunding.setValue(false);
		prefunding.setEnabled(false);
		submitButton.setEnabled(false); 

		fasterFunding.setValue(false);
		fasterFunding.setEnabled(false);

		terms.addValueChangeListener(e -> {
			boolean checked = e.getValue();

			if(checked) {
				prefunding.setEnabled(true);
				fasterFunding.setEnabled(true);
				submitButton.setEnabled(true); 
			} else {
				prefunding.setEnabled(false);
				fasterFunding.setEnabled(false);
				submitButton.setEnabled(false); 
			}
		});

		submitButton.addClickListener(e -> {
			Notification.show("Your options have been submitted.");
		});

		content.add(terms);
		optionsPanel.add(prefunding, fasterFunding, startDatePicker, submitButton);

		footer.setWidth("100%");
		footer.setPadding(true);
		footer.add(new H2("Footer"));

		// Compose layout
		center.add(content, optionsPanel);

		this.add(header, center, footer);
		this.expand(center);
	}

	private void greet() {
		VaadinRequest request = VaadinService.getCurrentRequest();

		String name = request.getParameter("name");

		if(name != null) {
			Notification.show("Hello, " + name);
		} else {
			Notification.show("Request from: " + request.getRemoteAddr());
		}
	}
}
