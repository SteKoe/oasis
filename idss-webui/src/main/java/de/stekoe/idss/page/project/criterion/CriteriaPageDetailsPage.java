package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CriteriaPageDetailsPage extends ProjectPage {
    @SpringBean
    private CriterionPageService criterionPageService;

    private LoadableDetachableModel<CriterionPage> criterionPageModel;

    public CriteriaPageDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        final StringValue pageId = pageParameters.get("pageId");
        criterionPageModel = new LoadableDetachableModel<CriterionPage>() {
            @Override
            protected CriterionPage load() {
                return criterionPageService.findById(pageId.toString());
            }
        };

        if(criterionPageModel.getObject() == null || !criterionPageModel.getObject().getProject().getId().equals(getProjectId())) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(SetOfCriteriaPage.class, new PageParameters().add("projectId", getProjectId()));
        }
    }
}
