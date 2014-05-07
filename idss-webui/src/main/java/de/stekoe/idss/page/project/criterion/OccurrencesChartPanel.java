package de.stekoe.idss.page.project.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.stekoe.amcharts.AmGraph;
import de.stekoe.amcharts.AmSerialChart;
import de.stekoe.amcharts.CategoryAxis;
import de.stekoe.amcharts.ChartCursor;
import de.stekoe.amcharts.ValueAxis;
import de.stekoe.amcharts.addition.Color;
import de.stekoe.idss.WebApplication;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ProjectService;

public class OccurrencesChartPanel extends Panel {
    private final AmSerialChart chart;

    @Inject
    UserChoiceRepository userChoiceRepository;

    @Inject
    CriterionService criterionService;

    @Inject
    ProjectService projectService;

    private final String htmlid;

    public OccurrencesChartPanel(String wicketId, IModel<Criterion> model) {
        super(wicketId, model);

        htmlid = "chart" + model.hashCode();
        chart = Chart.getChart();
        chart.setRotate(true);

        ModeReport modeReport = new ModeReport(model.getObject().getId());
        for(Entry<MeasurementValue, Integer> entry : modeReport.getCount().entrySet()) {
            List<Object> dataProvider = chart.getDataProvider();
            if(dataProvider == null) {
                chart.setDataProvider(new ArrayList<Object>());
            }

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("choice", entry.getKey().getValue());
            data.put("count", entry.getValue());
            data.put("count2", entry.getValue() * 2);
            chart.getDataProvider().add(data);
        }

        WebComponent webComponent = new WebComponent("chartdiv");
        webComponent.add(new AttributeModifier("id", htmlid));
        add(webComponent);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        Gson gson = new Gson();
        if(RuntimeConfigurationType.DEVELOPMENT.equals(WebApplication.get().getConfigurationType())) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        String string = String.format("var %1$s = AmCharts.makeChart(\"%1$s\", %2$s)", htmlid, gson.toJson(chart).toString());
        response.render(JavaScriptHeaderItem.forScript(string, null));
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

    static class Chart {
        private Chart() {}

        public static AmSerialChart getChart() {
            return new Chart().get();
        }

        private AmSerialChart get() {
            AmSerialChart chart = new AmSerialChart();
            chart.setType("serial");
            chart.setTheme("none");
            chart.setValueAxes(getValueAxes());
            chart.setGridAboveGraphs(true);
            chart.setStartDuration(1);
            chart.setGraphs(getGraphs());
            chart.setChartCursor(getChartCursor());
            chart.setCategoryField("choice");
            chart.setCategoryAxis(getCategoryAxis());
//            chart.setExportConfig(getExportConfig());
            return chart;
        }

        private CategoryAxis getCategoryAxis() {
            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.setGridPosition("start");
            categoryAxis.setGridAlpha(0);
            return categoryAxis;
        }

        private ChartCursor getChartCursor() {
            ChartCursor chartCursor = new ChartCursor();
            chartCursor.setCategoryBalloonEnabled(true);
            chartCursor.setCursorAlpha(0);
            chartCursor.setZoomable(false);
            return chartCursor;
        }

        private List<AmGraph> getGraphs() {
            List<AmGraph> list = new ArrayList<AmGraph>();

            AmGraph amGraph = new AmGraph();
            amGraph.setBalloonText("[[choice]]: <b>[[count]]</b>");
            amGraph.setFillAlphas(0.8);
            amGraph.setLineAlpha(0.2);
            amGraph.setType("column");
            amGraph.setValueField("count");
            list.add(amGraph);

            amGraph = new AmGraph();
            amGraph.setBalloonText("[[choice]]: <b>[[count]]</b>");
            amGraph.setFillAlphas(0.8);
            amGraph.setLineAlpha(0.2);
            amGraph.setType("column");
            amGraph.setValueField("count2");
            list.add(amGraph);

            return list;
        }

        private List<ValueAxis> getValueAxes() {
            List<ValueAxis> list = new ArrayList<ValueAxis>();

            ValueAxis valueAxis = new ValueAxis();
            valueAxis.setGridColor(Color.WHITE);
            valueAxis.setGridAlpha(0.2);
            valueAxis.setDashLength(0);
            list.add(valueAxis);

            return list;
        }
    }
}
