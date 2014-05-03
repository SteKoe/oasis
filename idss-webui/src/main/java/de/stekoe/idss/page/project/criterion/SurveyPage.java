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

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.page.project.ProjectDetailsPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;

public class SurveyPage extends ProjectPage {

    private static final Logger LOG = Logger.getLogger(SurveyPage.class);

    @Inject
    private CriterionPageService criterionPageService;
    private final List<CriterionPage> criterionPages;
    private int pageNum;

    private final CriterionPage currentPage;

    public SurveyPage(PageParameters pageParameters) {
        super(pageParameters);
        criterionPages = criterionPageService.findAllForProject(getProjectId());
        getPageNumFromUrl(pageParameters);
        currentPage = criterionPages.get(pageNum);

        add(new CriterionPagePanel("criterionPage", currentPage));
    }

    private void getPageNumFromUrl(PageParameters pageParameters) {
        StringValue pageValue = pageParameters.get("page");
        if(pageValue.isEmpty()) {
            pageNum = 0;
        } else {
            try {
                pageNum = pageValue.toInt();
            } catch(StringValueConversionException e) {
                LOG.info("PageNum for CriterionPage couldn't be converted. Falling back to pageNum = 0.", e);
                error("[Page does not exist!]");
                setResponsePage(ProjectDetailsPage.class, getPageParameters());
            }

            if(pageNum > criterionPages.size()) {
                error("[Page does not exist!]");
                setResponsePage(ProjectDetailsPage.class, getPageParameters());
            }
        }
    }
}
