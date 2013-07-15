package de.stekoe.idss.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.component.feedbackpanel.MyFeedbackPanel;
import de.stekoe.idss.component.navigation.main.MainNavigation;

public abstract class LayoutPage extends WebPage {
	private static final long serialVersionUID = 1860769875900411155L;

	public LayoutPage() {
		super();
		createContents();
	}

	public LayoutPage(IModel<?> model) {
		super(model);
		createContents();
	}

	public LayoutPage(PageParameters parameters) {
		super(parameters);
		createContents();
	}

	private void createContents() {
		add(new MyFeedbackPanel("systemmessages"));
		add(new MainNavigation("navbar"));
	}
}
