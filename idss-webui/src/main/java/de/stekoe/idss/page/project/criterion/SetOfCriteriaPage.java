package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import org.apache.wicket.AttributeModifier;
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

        add(listPageView());
        add(addPageButton());
    }

    private ListView<CriterionPage> listPageView() {
        return new ListView<CriterionPage>("page.list", loadableDetachableModel) {
            @Override
            protected void populateItem(ListItem<CriterionPage> item) {
                final CriterionPage criterionPage = item.getModelObject();
                item.add(new Label("id", criterionPage.getId()));

                item.add(deletePageLink(criterionPage));
                item.add(movePageUpLink(criterionPage));
                item.add(movePageDownLink(criterionPage));
            }

            private Link movePageUpLink(final CriterionPage criterionPage) {
                final Link link = new Link("move.page.up") {
                    @Override
                    public void onClick() {
                        criterionPageService.movePage(criterionPage, CriterionPageService.Direction.UP);
                    }

                    @Override
                    public boolean isVisible() {
                        return (criterionPage.getOrdering() > 1);
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.move.up")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }

            private Link movePageDownLink(final CriterionPage criterionPage) {
                final Link link = new Link("move.page.down") {
                    @Override
                    public void onClick() {
                    }

                    @Override
                    public boolean isVisible() {
                        return (criterionPage.getOrdering() < loadableDetachableModel.getObject().size());
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.move.down")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }

            private Link deletePageLink(final CriterionPage criterionPage) {
                final Link link = new Link("delete.page") {
                    @Override
                    public void onClick() {
                        criterionPageService.delete(criterionPage);
                        loadableDetachableModel.detach();
                        setResponsePage(getPage());
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.delete")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }
        };
    }

    private Link<Void> addPageButton() {
        return new Link<Void>("add.page") {
            @Override
            public void onClick() {
                CriterionPage criterionPage = new CriterionPage();
                criterionPage.setProject(getProject());
                criterionPageService.save(criterionPage);
                loadableDetachableModel.detach();

                setResponsePage(getPage());
            }
        };
    }
}
