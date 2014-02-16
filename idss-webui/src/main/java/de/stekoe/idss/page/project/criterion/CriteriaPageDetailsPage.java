package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CriteriaPageDetailsPage extends ProjectPage {
    @SpringBean
    private CriterionPageService criterionPageService;

    private List<CriterionPage> criterionPages;
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

        if(criterionPageModel.getObject() == null) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(SetOfCriteriaPage.class, new PageParameters().add("projectId", getProjectId()));
            return;
        }

        if(!getProjectId().equals(criterionPageModel.getObject().getProject().getId())) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(SetOfCriteriaPage.class, new PageParameters().add("projectId", getProjectId()));
            return;
        }

        criterionPages = criterionPageService.getCriterionPagesForProject(criterionPageModel.getObject().getProject().getId());

        addPageCounterLabel();

        addPrevPageLink();
        addNextPageLink();
    }

    private void addPageCounterLabel() {
        String pageCounter = MessageFormat.format(getString("label.page.counter"), criterionPageModel.getObject().getOrdering(), criterionPages.size());
        add(new Label("pageCounter", pageCounter));
    }

    private void addPrevPageLink() {
        final boolean hasPrevPage = criterionPageModel.getObject().getOrdering() > 1;

        final PageParameters parameters = new PageParameters(getPageParameters());
        if(hasPrevPage) {
            parameters.set("pageId", criterionPages.get(criterionPageModel.getObject().getOrdering() - 1 - 1 ).getId());
        }

        final BookmarkablePageLink<CriteriaPageDetailsPage> prevPageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.prev", CriteriaPageDetailsPage.class, parameters);
        add(prevPageLink);
        prevPageLink.setVisible(hasPrevPage);
    }

    private void addNextPageLink() {
        final boolean hasNextPage = criterionPageModel.getObject().getOrdering() < criterionPages.size();

        final PageParameters parameters = new PageParameters(getPageParameters());
        if(hasNextPage) {
            parameters.set("pageId", criterionPages.get(criterionPageModel.getObject().getOrdering() - 1 + 1).getId());
        }

        final BookmarkablePageLink<CriteriaPageDetailsPage> nextPageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.next", CriteriaPageDetailsPage.class, parameters);
        add(nextPageLink);
        nextPageLink.setVisible(hasNextPage);
    }
}
