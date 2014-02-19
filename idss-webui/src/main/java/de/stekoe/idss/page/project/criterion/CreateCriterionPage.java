package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.enums.CriterionType;
import de.stekoe.idss.page.project.ProjectPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateCriterionPage extends ProjectPage {
    public CreateCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        final String criterionTypeCandidate = pageParameters.get("type").toString();

        CriterionType criterionType = null;
        try {
            criterionType = CriterionType.valueOf(criterionTypeCandidate);
        } catch(IllegalArgumentException e) {
            getSession().error("Desired type does not exist.");
            setResponsePage(SelectCriterionPage.class, getPageParameters());
            return;
        }

        final Form form = new Form("form");
        add(form);

        form.add(new TextField<String>("criterionType", Model.of(getString(criterionType.getKey()))));
        form.add(new WebMarkupContainer("table.empty"));

        form.add(new ListView<String>("table.entries") {
            @Override
            protected void populateItem(ListItem<String> item) {
                item.add(new Label("table.entry.key"));
                item.add(new Label("table.entry.value"));
            }
        });
    }
}
