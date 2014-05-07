package de.stekoe.idss.page.project.criterion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;
import de.stekoe.idss.session.WebSession;


public class CriterionPagePanel extends Panel {

    @Inject
    UserChoiceRepository userChoiceRepository;

    private static final Logger LOG = Logger.getLogger(CriterionPagePanel.class);

    private final CriterionPage criterionPage;
    private final List<PageElement> pageElements;
    private final List<PageElementPanel> pageElementPanels = new ArrayList<PageElementPanel>();

    public CriterionPagePanel(final String wicketId, final CriterionPage criterionPage) {
        super(wicketId);
        this.criterionPage = criterionPage;
        this.pageElements = criterionPage.getPageElements();

        Form form = new Form("survey.form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                Iterator<PageElementPanel> iterator = pageElementPanels.iterator();
                while(iterator.hasNext()) {
                    PageElementPanel next = iterator.next();

                    IModel<UserChoice> model = next.getModel();
                    UserChoice choice = model.getObject();
                    choice.setUser(WebSession.get().getUser());
                    choice.setProject(criterionPage.getProject());

                    userChoiceRepository.save(choice);
                }
                setResponsePage(getPage());
            }
        };
        add(form);

        ListView<PageElement> listView = new ListView<PageElement>("elements", this.pageElements) {
            @Override
            protected void populateItem(ListItem<PageElement> item) {
                PageElement pageElement = item.getModelObject();

                Component element = new Label("element");
                if(pageElement instanceof NominalScaledCriterion) {
                    NominalScaledCriterion nsc = (NominalScaledCriterion) pageElement;
                    if(!nsc.isMultipleChoice()) {
                        element = new NominalScaledCriterionSingleChoicePanel("element", nsc);
                    } else {
                        element = new NominalScaledCriterionMultipleChoicePanel("element", nsc);
                    }
                } else if(pageElement instanceof OrdinalScaledCriterion) {
                    element = new OrdinalScaledCriterionSingleChoicePanel("element", (OrdinalScaledCriterion) pageElement);
                }

                if(element instanceof PageElementPanel) {
                    pageElementPanels.add((PageElementPanel) element);
                }
                item.add(element);
            }
        };
        listView.setReuseItems(true);
        form.add(listView);
    }

}
