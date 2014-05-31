package de.stekoe.idss.page.project.criterion.page;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrderableUtil.Direction;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.session.WebSession;


public class CriterionGroupElementPanel extends Panel {

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    CriterionPageService criterionPageService;

    private final CriterionGroup criterionGroup;
    private final CriterionPage criterionPage;


    public CriterionGroupElementPanel(String wicketId, final CriterionGroup criterionGroup) {
        super(wicketId);
        this.criterionGroup = criterionGroup;
        this.criterionPage = criterionGroup.getCriterionPage();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("group.name", criterionGroup.getName()));

        CriterionPageElementListView itemList = new CriterionPageElementListView("group.items", criterionGroup.getCriterions());
        add(itemList);

        final WebMarkupContainer emptyTable = new WebMarkupContainer("page.empty");
        add(emptyTable);
        emptyTable.setVisible(itemList.getList() == null || itemList.getList().isEmpty());

        Link moveUpLink = new Link("move.up") {
            @Override
            public void onClick() {
                if(criterionPage.move(criterionGroup, Direction.UP)) {
                    criterionPageService.save(criterionPage);
                    WebSession.get().success(getString("message.save.success"));
                } else {
                    WebSession.get().error(getString("message.save.error"));
                }
                setResponsePage(getPage());
                return;
            }
        };
        add(moveUpLink);
        moveUpLink.setVisible(criterionPage.getPageElements().indexOf(criterionGroup) > 0);

        Link moveDownLink = new Link("move.down") {
            @Override
            public void onClick() {
                if(criterionPage.move(criterionGroup, Direction.DOWN)) {
                    criterionPageService.save(criterionPage);
                    WebSession.get().success(getString("message.save.success"));
                } else {
                    WebSession.get().error(getString("message.save.error"));
                }
                setResponsePage(getPage());
                return;
            }
        };
        add(moveDownLink);
        moveDownLink.setVisible(criterionPage.getPageElements().indexOf(criterionGroup) < criterionPage.getPageElements().size() - 1);

        add(new Link<Void>("group.delete"){
            @Override
            public void onClick() {
                criterionGroupService.delete(criterionGroup.getId());
                WebSession.get().success(getString("message.delete.success"));
            }
        });

        add(new BookmarkablePageLink<EditCriterionGroupPage>("group.edit", EditCriterionGroupPage.class, getPage().getPageParameters().mergeWith(new PageParameters().add("criterionGroupId", criterionGroup.getId()))));
    }

}
