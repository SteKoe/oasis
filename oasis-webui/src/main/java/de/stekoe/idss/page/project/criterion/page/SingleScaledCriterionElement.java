/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.page.project.criterion.page;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrderableUtil.Direction;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditNominalCriterionPage;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.session.WebSession;

public class SingleScaledCriterionElement extends Panel {

    @SpringBean
    private CriterionService criterionService;

    @Inject
    private CriterionPageService criterionPageService;

    @Inject
    private CriterionGroupService criterionGroupService;

    private final CompoundPropertyModel<SingleScaledCriterion> sscModel;
    private final CriterionPage parentPage;
    private CriterionGroup parentGroup;

    public SingleScaledCriterionElement(String aId, SingleScaledCriterion ssc) {
        super(aId);
        parentPage = ssc.getCriterionPage();
        if(parentPage == null) {
            ArrayList<CriterionGroup> list = new ArrayList<CriterionGroup>(ssc.getCriterionGroups());
            parentGroup = list.get(0);
        }
        sscModel = new CompoundPropertyModel<SingleScaledCriterion>(ssc);
        setDefaultModel(new CompoundPropertyModel<SingleScaledCriterion>(ssc));
    }

    private int getCriterionCount() {
        if(parentGroup != null) {
            return parentGroup.getCriterions().size();
        }
        if(parentPage != null) {
            return parentPage.getPageElements().size();
        }
        return 0;
    }

    private int getIndexOf(SingleScaledCriterion ssc) {
        if(parentPage != null) {
            return parentPage.getPageElements().indexOf(ssc);
        }
        if(parentGroup != null) {
            return parentGroup.getCriterions().indexOf(ssc);
        }
        return -1;
    }

    private boolean move(SingleScaledCriterion ssc, Direction direction) {
        if(parentPage != null) {
            return parentPage.move(ssc, direction);
        }
        if(parentGroup != null) {
            return parentGroup.move(ssc, direction);
        }

        return false;
    }

    private void save() {
        if(parentPage != null) {
            criterionPageService.save(parentPage);
            return;
        }
        if(parentGroup != null) {
            criterionGroupService.save(parentGroup);
            return;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final SingleScaledCriterion ssc = (SingleScaledCriterion) getDefaultModelObject();

        add(new Label("name", ssc.getName()));
        add(new Label("type", getType()));

        Link moveUpLink = new Link("move.up") {
            @Override
            public void onClick() {
                if(move(ssc, Direction.UP)) {
                    WebSession.get().success(getString("message.save.success"));
                    save();
                } else {
                    WebSession.get().error(getString("message.save.error"));
                }
                setResponsePage(getPage());
                return;
            }
        };
        add(moveUpLink);
        moveUpLink.setVisible(getIndexOf(ssc) > 0);

        Link moveDownLink = new Link("move.down") {
            @Override
            public void onClick() {
                if(move(ssc, Direction.DOWN)) {
                    WebSession.get().success(getString("message.save.success"));
                    save();
                } else {
                    WebSession.get().error(getString("message.save.error"));
                }
                setResponsePage(getPage());
                return;
            }
        };
        add(moveDownLink);
        moveDownLink.setVisible(getIndexOf(ssc) < getCriterionCount()-1);

        // Edit Link
        WebMarkupContainer link = new WebMarkupContainer("edit");
        if(ssc instanceof OrdinalScaledCriterion) {
            link = new BookmarkablePageLink<EditOrdinalCriterionPage>("edit", EditOrdinalCriterionPage.class, new PageParameters(getPage().getPageParameters()).add("criterionId", ssc.getId()));
        } else if(ssc instanceof NominalScaledCriterion) {
            link = new BookmarkablePageLink<EditNominalCriterionPage>("edit", EditNominalCriterionPage.class, new PageParameters(getPage().getPageParameters()).add("criterionId", ssc.getId()));
        }
        add(link);

        add(new Link("delete") {
            @Override
            public void onClick() {
                final SingleScaledCriterion ssc = sscModel.getObject();

                final CriterionPage criterionPage = ssc.getCriterionPage();
                criterionService.delete(ssc.getId());
            }
        });
    }

    private String getType() {
        SingleScaledCriterion criterion = sscModel.getObject();
        if(criterion instanceof NominalScaledCriterion) {
            return getString("label.criterion.type.nominal");
        } else if(criterion instanceof OrdinalScaledCriterion) {
            return getString("label.criterion.type.ordinal");
        }

        return "";
    }
}
