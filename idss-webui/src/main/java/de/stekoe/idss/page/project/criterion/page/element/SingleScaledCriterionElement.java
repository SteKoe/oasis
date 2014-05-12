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

package de.stekoe.idss.page.project.criterion.page.element;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditNominalCriterionPage;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;

public class SingleScaledCriterionElement extends Panel {

    @SpringBean
    private CriterionService criterionService;

    @SpringBean
    private CriterionPageService criterionPageService;

    private final CompoundPropertyModel<SingleScaledCriterion> sscModel;

    public SingleScaledCriterionElement(String aId, SingleScaledCriterion aSingleScaledCriterion) {
        super(aId);
        sscModel = new CompoundPropertyModel<SingleScaledCriterion>(aSingleScaledCriterion);
        setDefaultModel(new CompoundPropertyModel<SingleScaledCriterion>(aSingleScaledCriterion));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        SingleScaledCriterion ssc = (SingleScaledCriterion) getDefaultModelObject();

        add(new Label("name"));
        add(new Label("type", getType()));

        if(ssc instanceof OrdinalScaledCriterion) {
             add(new BookmarkablePageLink<EditOrdinalCriterionPage>("edit", EditOrdinalCriterionPage.class, new PageParameters(getPage().getPageParameters()).add("criterionId", ssc.getId())));
        } else if(ssc instanceof NominalScaledCriterion) {
            add(new BookmarkablePageLink<EditNominalCriterionPage>("edit", EditNominalCriterionPage.class, new PageParameters(getPage().getPageParameters()).add("criterionId", ssc.getId())));
        } else {
            add(new Link("edit"){
                @Override
                public void onClick() {

                }});
        }
        add(new Link("delete") {
            @Override
            public void onClick() {
                final SingleScaledCriterion ssc = sscModel.getObject();
                final CriterionPage criterionPage = ssc.getCriterionPage();
                criterionPage.getPageElements().remove(ssc);
                criterionPageService.save(criterionPage);
                criterionService.deleteCriterion(ssc.getId());
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
