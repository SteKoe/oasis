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

package de.stekoe.idss.page.project.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.repository.UserChoiceRepository;
import de.stekoe.idss.service.CriterionService;

public class ResultPage extends ProjectPage {

    @Inject
    UserChoiceRepository userChoiceRepository;

    @Inject
    CriterionService criterionService;

    public ResultPage(PageParameters pageParameters) {
        super(pageParameters);

        final ModeReport modeReport = new ModeReport("f6b482a8-69de-4487-857e-fe35c53843a6");
        add(new Label("name", modeReport.getModel().getObject().getName()));
        add(new ListView<MeasurementValue>("test", new ArrayList(modeReport.getCount().keySet())) {
            @Override
            protected void populateItem(ListItem<MeasurementValue> item) {
                MeasurementValue modelObject = item.getModelObject();
                int count = modeReport.getCount().get(modelObject);

                StringBuilder sb = new StringBuilder();
                sb.append(modelObject.getValue());
                sb.append(": ");
                sb.append(count);

                item.add(new Label("test.1", sb.toString()));
            }
        });
    }


    class ModeReport implements Serializable {
        private final Map<MeasurementValue, Integer> count = new LinkedHashMap<MeasurementValue, Integer>();

        private final ModeReportModel model;

        private final List<UserChoice> chosenValues;

        public ModeReport(String criterionId) {
            model = new ModeReportModel(criterionId);
            chosenValues = userChoiceRepository.findByCriterionId(criterionId);

            List<MeasurementValue> values = getModel().getObject().getValues();
            for(MeasurementValue mv : values) {
                getCount().put(mv, 0);
            }

            for(UserChoice uc : chosenValues) {
                for(MeasurementValue mv : uc.getMeasurementValues()) {
                    int value = getCount().get(mv);
                    getCount().put(mv, value+1);
                }
            }
        }

        public Map<MeasurementValue, Integer> getCount() {
            return count;
        }

        public ModeReportModel getModel() {
            return model;
        }

        class ModeReportModel extends Model<SingleScaledCriterion> {
            private final String criterionId;
            private SingleScaledCriterion criterion;

            public ModeReportModel(String criterionId) {
                this.criterionId = criterionId;
            }

            @Override
            public SingleScaledCriterion getObject() {
                if(this.criterion != null) {
                    return this.criterion;
                }

                if(this.criterionId != null) {
                    this.criterion = criterionService.findSingleScaledCriterionById(criterionId);
                    return this.criterion;
                }

                return null;
            }

            @Override
            public void detach() {
                this.criterion = null;
            }
        }
    }
}
