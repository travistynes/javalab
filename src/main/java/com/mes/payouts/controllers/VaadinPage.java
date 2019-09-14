package com.mes.payouts.controllers;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Access this page at: https://localhost:8080/javalab/vaadin
 *
 * Vaadin API doc: https://vaadin.com/api/platform/14.0.4/overview-summary.html
 *
 * See all Vaadin components (checkbox, text field, date picker, etc) at
 * https://vaadin.com/components
 */
@Route(value="vaadin")
public class VaadinPage extends VerticalLayout {
	public VaadinPage() {
		this.createUI();
	}

	private void createUI() {
		HorizontalLayout header = new HorizontalLayout();
		HorizontalLayout center = new HorizontalLayout();
		VerticalLayout navBar = new VerticalLayout();
		VerticalLayout content = new VerticalLayout();
		HorizontalLayout footer = new HorizontalLayout();

		// Configure layouts
		this.setSizeFull();
		this.setPadding(false);
		this.setSpacing(false);

		header.setWidth("100%");
		header.setPadding(true);
		header.add(new H2("Header"));

		navBar.setWidth("200px");
		navBar.add(new H3("Navigation"));

		center.setWidth("100%");
		content.setWidth("100%");
		content.add(new H3("Signup Form"));
		
		// Checkboxes: https://vaadin.com/components/vaadin-checkbox/java-examples
		Checkbox terms = new Checkbox("Accept Terms & Conditions");
		Checkbox prefunding = new Checkbox("Prefunding");
		Checkbox fasterFunding = new Checkbox("Faster Funding");

		terms.setValue(false);

		prefunding.setValue(false);
		prefunding.setEnabled(false);

		fasterFunding.setValue(false);
		fasterFunding.setEnabled(false);

		terms.addValueChangeListener(e -> {
			boolean checked = e.getValue();

			if(checked) {
				prefunding.setEnabled(true);
				fasterFunding.setEnabled(true);
			} else {
				prefunding.setEnabled(false);
				fasterFunding.setEnabled(false);
			}
		});

		// Submit button
		Button submitButton = new Button("Submit", VaadinIcon.CHECK.create());
		submitButton.addClickListener(e -> {
			Notification.show("Your options have been submitted.");
		});

		content.add(terms, prefunding, fasterFunding, submitButton);

		footer.setWidth("100%");
		footer.setPadding(true);
		footer.add(new H2("Footer"));

		// Compose layout
		center.add(navBar, content);
		center.setFlexGrow(1, navBar);

		this.add(header, center, footer);
		this.expand(center);
	}
}
