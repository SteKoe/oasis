package de.stekoe.idss.page;

import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends LayoutPage {
	private static final long serialVersionUID = 1L;
	
	private Logger LOG = Logger.getLogger(HomePage.class);
	
	public HomePage() {
		this(null);
	}
	
	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		info("Infotext");
		error("Errortext");
		warn("Warning!");
		success("Success");
    }
}
