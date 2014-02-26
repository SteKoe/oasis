package de.stekoe.idss.page.project.criterion.page;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

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

        if (criterionPageModel.getObject() == null) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(CriteriaPageListPage.class, new PageParameters().add("projectId", getProjectId()));
            return;
        }

        if (!getProjectId().equals(criterionPageModel.getObject().getProject().getId())) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(CriteriaPageListPage.class, new PageParameters().add("projectId", getProjectId()));
            return;
        }

        criterionPages = criterionPageService.getCriterionPagesForProject(criterionPageModel.getObject().getProject().getId());

        add(new ListView<CriterionPage>("page.list", criterionPages) {
            @Override
            protected void populateItem(ListItem<CriterionPage> item) {
                final CriterionPage criterionPage = item.getModelObject();

                final PageParameters parameters = new PageParameters(getPageParameters());
                parameters.set("pageId", criterionPage.getId());

                final BookmarkablePageLink<CriteriaPageDetailsPage> pageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.link", CriteriaPageDetailsPage.class, parameters);
                item.add(pageLink);
                pageLink.setBody(Model.of(criterionPage.getOrdering()));
                if (criterionPageModel.getObject().getId().equals(criterionPage.getId())) {
                    item.add(AttributeModifier.append("class", "active"));
                }
            }
        });

        addPrevPageLink();
        addNextPageLink();
    }

    private void addPrevPageLink() {
        final boolean hasPrevPage = criterionPageModel.getObject().getOrdering() > 1;

        final WebMarkupContainer component = new WebMarkupContainer("page.prev");
        add(component);

        final PageParameters parameters = new PageParameters(getPageParameters());
        if (hasPrevPage) {
            parameters.set("pageId", criterionPages.get(criterionPageModel.getObject().getOrdering() - 1 - 1).getId());
        } else {
            component.add(AttributeModifier.append("class", "disabled"));
        }

        final BookmarkablePageLink<CriteriaPageDetailsPage> prevPageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.prev.link", CriteriaPageDetailsPage.class, parameters);
        component.add(prevPageLink);
    }

    private void addNextPageLink() {
        final boolean hasNextPage = criterionPageModel.getObject().getOrdering() < criterionPages.size();

        final WebMarkupContainer component = new WebMarkupContainer("page.next");
        add(component);

        final PageParameters parameters = new PageParameters(getPageParameters());
        if (hasNextPage) {
            parameters.set("pageId", criterionPages.get(criterionPageModel.getObject().getOrdering() - 1 + 1).getId());
        } else {
            component.add(AttributeModifier.append("class", "disabled"));
        }

        final BookmarkablePageLink<CriteriaPageDetailsPage> nextPageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.next.link", CriteriaPageDetailsPage.class, parameters);
        component.add(nextPageLink);
    }
}
