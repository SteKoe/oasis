package de.stekoe.idss.reports;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.amcharts.AmChart;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.repository.UserChoiceRepository;

public class OrdinalChartPanel extends ChartPanel {

    @Inject
    UserChoiceRepository userChoiceRepository;

    private final OrdinalChartReport report;

    public OrdinalChartPanel(String wicketId, IModel<Criterion> model) {
        super(wicketId, model);

        report = new OrdinalChartReport();
        report.setUserChoiceRepository(userChoiceRepository);
        report.setCriterion(model.getObject());
    }

    @Override
    protected AmChart getChart() {
        return report.getResult();
    }

}
