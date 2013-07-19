package de.stekoe.idss.page;

import org.apache.wicket.RestartResponseAtInterceptPageException;

import de.stekoe.idss.IDSSSession;

@SuppressWarnings("serial")
public class LogoutPage extends LayoutPage {
	public LogoutPage() {
		IDSSSession.get().invalidate();
		info("Sie haben sich erfolgreich abgemeldet!");
		throw new RestartResponseAtInterceptPageException(HomePage.class);
	}
}
