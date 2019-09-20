package com.mes.payouts.security;

import org.springframework.stereotype.Component;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.component.UI;
import com.mes.payouts.ui.login.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UIServiceInitListener implements VaadinServiceInitListener {
	private static final Logger log = LoggerFactory.getLogger(UIServiceInitListener.class);

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(e -> {
			final UI ui = e.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/**
	 * Spring Security is configured to authenticate all requests.
	 * Only requests that pass there will make it to this listener, but this is
	 * a way to redirect unauthenticated users in the absense of Spring Security.
	 */
	private void beforeEnter(BeforeEnterEvent event) {
		if(!LoginPage.class.equals(event.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(LoginPage.class);
		}
	}
}
