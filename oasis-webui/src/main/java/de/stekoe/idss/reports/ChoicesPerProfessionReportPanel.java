package de.stekoe.idss.reports;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.amcharts.AmChart;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.repository.UserChoiceRepository;

public class ChoicesPerProfessionReportPanel extends ChartPanel {

    @Inject
    UserChoiceRepository userChoiceRepository;
    private final ChoicesPerProfessionReport report;

    public ChoicesPerProfessionReportPanel(String wicketId, IModel<Criterion> model) {
        super(wicketId, model);

        report = new ChoicesPerProfessionReport();
        report.setUserChoiceRepository(userChoiceRepository);
        report.setCriterion(model.getObject());

        // Is executed by "setCriterion"... weird
//        report.run();
    }

    @Override
    protected AmChart getChart() {
        return report.getResult();
    }
}
