package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SetOfCriteriaPage extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private CriterionPageService criterionPageService;

    private final LoadableDetachableModel<List<CriterionPage>> loadableDetachableModel = new LoadableDetachableModel<List<CriterionPage>>() {
        @Override
        protected List<CriterionPage> load() {
            return criterionPageService.getCriterionPagesForProject(getProjectId());
        }
    };


    public SetOfCriteriaPage(PageParameters pageParameters) {
        super(pageParameters);

        final List<CriterionPage> criterionPages = getCriterionPagesForCurrentProject();

        add(new ListView<CriterionPage>("page.list", criterionPages) {
            @Override
            protected void populateItem(ListItem<CriterionPage> item) {
                final CriterionPage criterionPage = item.getModelObject();
                item.add(new Label("id", criterionPage.getOrdering()));
                item.add(new Link("delete.page") {
                    @Override
                    public void onClick() {
                        criterionPageService.delete(criterionPage);
                        setResponsePage(getPage());
                    }
                });
            }
        });


        add(new Link<Void>("add.page") {
            @Override
            public void onClick() {
                CriterionPage criterionPage = new CriterionPage();
                criterionPage.setProject(getProject());
                criterionPageService.save(criterionPage);

                setResponsePage(getPage());
            }
        });
    }

    private List<CriterionPage> getCriterionPagesForCurrentProject() {
        return loadableDetachableModel.getObject();
    }
}
