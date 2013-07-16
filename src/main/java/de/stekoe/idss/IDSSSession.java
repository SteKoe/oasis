package de.stekoe.idss;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import de.stekoe.idss.model.User;

@SuppressWarnings("serial")
public class IDSSSession extends WebSession {

	private User user;

	public IDSSSession(Request request) {
		super(request);
		setLocale(Locale.ENGLISH);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static IDSSSession get()
	{
		return (IDSSSession)Session.get();
	}
}
