package com.mes.payouts.security;

import org.springframework.stereotype.Component;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.component.UI;
import com.mes.payouts.controllers.LoginPage;

@Component
public class UIServiceInitListener implements VaadinServiceInitListener {
	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(e -> {
			final UI ui = e.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	private void beforeEnter(BeforeEnterEvent event) {
		if(!LoginPage.class.equals(event.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(LoginPage.class);
		}
	}
}
