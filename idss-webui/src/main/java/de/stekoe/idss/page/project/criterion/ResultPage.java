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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.time.Duration;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.reports.CSVReport;
import de.stekoe.idss.reports.OccurrencesChartPanel;
import de.stekoe.idss.reports.OrdinalChartPanel;

public class ResultPage extends ProjectPage {

    @Inject
    CSVReport csvReport;

    public ResultPage(PageParameters pageParameters) {
        super(pageParameters);

        IModel<File> fileModel = new AbstractReadOnlyModel<File>() {
            @Override
            public File getObject() {
                csvReport.setCriterions(getProject().getScaleList());

                File tempFile;
                try
                {
                    tempFile = File.createTempFile("oasis-csv-report-", ".tmp");
                    InputStream data = new ByteArrayInputStream(csvReport.getResult().getBytes());
                    Files.writeTo(tempFile, data);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }

                return tempFile;
            }
        };

        final DownloadLink downloadLink = new DownloadLink("export.csv", fileModel, "data.csv");
        downloadLink.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);
        add(downloadLink);

        List<Criterion> scaleList = getProject().getScaleList();
        ListView<Criterion> listView = new ListView<Criterion>("charts", scaleList) {
            @Override
            protected void populateItem(ListItem<Criterion> item) {
                item.add(new Label("criterion.name", item.getModelObject().getName()));
                if(item.getModelObject() instanceof NominalScaledCriterion) {
                    item.add(new OccurrencesChartPanel("chart", item.getModel()));
                } else {
                    item.add(new OrdinalChartPanel("chart", item.getModel()));
                }
            }
        };
        add(listView);

    }
}
