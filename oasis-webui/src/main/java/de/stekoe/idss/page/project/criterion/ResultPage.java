package de.stekoe.idss.page.project.criterion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.time.Duration;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.reports.CSVReport;
import de.stekoe.idss.reports.ChoicesPerProfessionReportPanel;
import de.stekoe.idss.reports.OccurrencesChartPanel;
import de.stekoe.idss.reports.OrdinalChartPanel;
import de.stekoe.idss.service.CriterionService;
public class ResultPage extends ProjectPage {

    @Inject
    CSVReport csvReport;

    @Inject
    CriterionService criterionService;

    public ResultPage(PageParameters pageParameters) {
        super(pageParameters);

        IModel<File> fileModel = new AbstractReadOnlyModel<File>() {
            @Override
            public File getObject() {
                csvReport.setCriterions(criterionService.findAllForReport(getProject().getId()));

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

        List<Criterion> scaleList = criterionService.findAllForReport(getProject().getId());
        ListView<Criterion> listView = new ListView<Criterion>("charts", scaleList) {
            @Override
            protected void populateItem(ListItem<Criterion> item) {
                if(item == null || item.getModel() == null) {
                    return;
                }

                item.add(new Label("criterion.name", item.getModelObject().getName()));

                List<Panel> chartsList = new ArrayList<Panel>();
                if(item.getModelObject() instanceof NominalScaledCriterion) {
                    chartsList.add(new OccurrencesChartPanel("chart", item.getModel()));
                } else {
                    chartsList.add(new ChoicesPerProfessionReportPanel("chart", item.getModel()));
                    chartsList.add(new OrdinalChartPanel("chart", item.getModel()));
                }

                item.add(new ListView<Panel>("charts.inner", chartsList) {
                    @Override
                    protected void populateItem(ListItem<Panel> item) {
                        item.add(item.getModelObject());
                    }
                });
            }
        };
        add(listView);

    }
}
