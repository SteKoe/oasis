package de.stekoe.idss.page.project.criterion.page;

import java.util.ArrayList;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrderableUtil.Direction;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.page.project.criterion.SelectCriterionPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;

public class CriteriaPageListPage extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private CriterionPageService criterionPageService;

    private final IModel<ArrayList<CriterionPage>> criterionPagesModel = new CriterionPageModel(getProjectId());


    public CriteriaPageListPage(PageParameters pageParameters) {
        super(pageParameters);

        addListPageView();
        addNewPageButton();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(new JavaScriptContentHeaderItem("", "pagesorting", null));;
    }

    private void addListPageView() {
        final ListView<CriterionPage> listView = new ListView<CriterionPage>("page.list", criterionPagesModel) {
            @Override
            protected void populateItem(ListItem<CriterionPage> item) {
                final CriterionPage criterionPage = item.getModelObject();

                item.add(new AttributeModifier("data-id", criterionPage.getId()));

                item.add(new Label("page.index", (criterionPage.getOrdering() + 1)));

                item.add(movePageUpLink(criterionPage));
                item.add(movePageDownLink(criterionPage));

                item.add(deletePageLink(criterionPage));
                item.add(new BookmarkablePageLink<SelectCriterionPage>("page.add.criterion", SelectCriterionPage.class, new PageParameters(getPageParameters()).add("pageId", criterionPage.getId())));

                final ListView<PageElement> pageListItems = new CriterionPageElementListView("page.list.items", criterionPage.getPageElements());
                item.add(pageListItems);

                final WebMarkupContainer emptyTable = new WebMarkupContainer("page.empty");
                item.add(emptyTable);
                emptyTable.setVisible(pageListItems.getList() == null || pageListItems.getList().isEmpty());
            }

            private Link movePageUpLink(final CriterionPage criterionPage) {
                final Link link = new Link("move.page.up") {
                    @Override
                    public void onClick() {
                        criterionPageService.move(criterionPage, Direction.UP);
                        criterionPagesModel.detach();
                        setResponsePage(getPage());
                    }

                    @Override
                    public boolean isVisible() {
                        return (criterionPage.getOrdering() > 0);
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
                        criterionPageService.move(criterionPage, Direction.DOWN);
                        criterionPagesModel.detach();
                        setResponsePage(getPage());
                    }

                    @Override
                    public boolean isVisible() {
                        return (criterionPage.getOrdering() < criterionPagesModel.getObject().size() - 1);
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.move.down")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }

            private Link deletePageLink(final CriterionPage criterionPage) {
                final Link link = new Link("page.delete") {
                    @Override
                    public void onClick() {
                        criterionPageService.delete(criterionPage.getId());
                        setResponsePage(getPage());
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.delete")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }
        };
        add(listView);
    }

    private void addNewPageButton() {
        final Link<Void> newPageButton = new Link<Void>("add.page") {
            @Override
            public void onClick() {
                CriterionPage criterionPage = new CriterionPage();
                criterionPage.setProject(getProject());
                criterionPageService.save(criterionPage);

                setResponsePage(getPage());
            }
        };
        add(newPageButton);
    }

    private class CriterionPageModel extends Model<ArrayList<CriterionPage>> {
        private final String projectId;
        private ArrayList<CriterionPage> modelObject;

        public CriterionPageModel(String projectId) {
            this.projectId = projectId;
        }

        @Override
        public ArrayList<CriterionPage> getObject() {
            if(projectId == null) {
                return new ArrayList<CriterionPage>();
            } else {
                return (ArrayList<CriterionPage>) criterionPageService.getCriterionPagesForProject(projectId);
            }
        }
    }
}
