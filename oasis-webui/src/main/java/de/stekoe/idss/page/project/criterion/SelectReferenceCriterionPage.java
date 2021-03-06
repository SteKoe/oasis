package de.stekoe.idss.page.project.criterion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.page.project.criterion.page.CriteriaPageListPage;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ReferenceCriterionGroupService;

public class SelectReferenceCriterionPage extends ProjectPage {
    private static final Logger LOG = Logger.getLogger(SelectReferenceCriterionPage.class);

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    CriterionPageService criterionPageService;

    @Inject
    CriterionService criterionService;

    private List<CriterionGroup> selectedGroups = new ArrayList<CriterionGroup>();
    private CriterionPage criterionPage;

    public SelectReferenceCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        String criterionPageId = pageParameters.get("pageId").toString(null);
        if(criterionPageId != null) {
            criterionPage = criterionPageService.findOne(criterionPageId);
        }

        Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                List<CriterionGroup> selectedCritionGroups = getSelectedGroups();
                for (CriterionGroup criterionGroup : selectedCritionGroups) {

                    for(Criterion c : criterionGroup.getCriterions()) {
                        if(c instanceof SingleScaledCriterion) {
                            SingleScaledCriterion<MeasurementValue> ssc = (SingleScaledCriterion<MeasurementValue>) c;
                            for (MeasurementValue mv : ssc.getValues()) {
                                mv.setCriterion(ssc);
                            }
                            ssc.getCriterionGroups().add(criterionGroup);
                            criterionService.save(ssc);
                        }
                    }

                    criterionGroup.setCriterionPage(criterionPage);
                    criterionPage.getPageElements().add(criterionGroup);
                    criterionPageService.save(criterionPage);
                }

                setResponsePage(CriteriaPageListPage.class, getPageParameters());
            }
        };
        add(form);

        // ListView of Reference CriterionGroups with its ReferenceCriterions
        ArrayList<CriterionGroup> referenceGroups = (ArrayList<CriterionGroup>) referenceCriterionGroupService.findAll();
        filterEmptyGroups(referenceGroups);
        ListView<CriterionGroup> listView = new ListView<CriterionGroup>("panel", new Model<ArrayList<CriterionGroup>>(referenceGroups)) {
            @Override
            protected void populateItem(ListItem<CriterionGroup> item) {
                CriterionGroup criterionGroup = item.getModelObject();

                WebMarkupContainer panelTitle = new WebMarkupContainer("panel.title");
                item.add(panelTitle);
                panelTitle.add(new AttributeModifier("href", "#collapse" + item.getIndex()));

                panelTitle.add(new Label("criteriongroup.name", criterionGroup.getName()));

                WebMarkupContainer panelCollapse = new WebMarkupContainer("panel.collapse");
                item.add(panelCollapse);
                panelCollapse.add(new AttributeModifier("id", "collapse" + item.getIndex()));

                WebMarkupContainer panelBody = new WebMarkupContainer("panel.body");
                panelCollapse.add(panelBody);

                panelBody.add(criterionList(criterionGroup));
            }

            private Component criterionList(final CriterionGroup criterionGroup) {
                List<Criterion> criterions = criterionGroup.getCriterions();
                return new ListView<Criterion>("criterion.list", criterions) {
                    @Override
                    protected void populateItem(ListItem<Criterion> item) {
                        Criterion citerion = item.getModelObject();
                        item.add(new CheckBox("criterion.checkbox", new SelectReferenceCriterionModel(criterionGroup, citerion, getSelectedGroups())));
                        item.add(new Label("criterion.name", citerion.getName()));
                    }
                };
            }
        };
        form.add(listView);
    }

    /**
     * We do not want to present the user empty lists of criterions which are not selectable.
     *
     * @param referenceGroups List of referenceCriterionGroups which are filtered
     */
    private void filterEmptyGroups(ArrayList<CriterionGroup> referenceGroups) {
        CollectionUtils.filter(referenceGroups, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if(object instanceof CriterionGroup && ((CriterionGroup) object).getCriterions().size() > 0) {
                    return true;
                }

                return false;
            }
        });
    }

    public List<CriterionGroup> getSelectedGroups() {
        return selectedGroups;
    }

    public void setSelectedGroups(List<CriterionGroup> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }
}
