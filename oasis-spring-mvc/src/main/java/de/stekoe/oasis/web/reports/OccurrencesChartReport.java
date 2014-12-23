package de.stekoe.oasis.web.reports;

import de.stekoe.amcharts.*;
import de.stekoe.amcharts.addition.Color;
import de.stekoe.idss.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OccurrencesChartReport extends SingleCriterionReport<AmSerialChart> {
    private Map<String, Integer> count;

    public OccurrencesChartReport(Criterion criterion, List<UserChoices> userChoiceses) {
        super(criterion, userChoiceses);
    }

    @Override
    public void run() {
        count = new HashMap<>();

        getColumns().stream().forEach(col -> {
            if (col instanceof NominalScaledCriterion) {
                NominalScaledCriterion nsc = (NominalScaledCriterion) col;
                List<NominalValue> values = nsc.getValues();
                values.stream().forEach(val -> {
                    if (count.containsKey(val.getValue()) == false) {
                        count.put(val.getValue(), 0);
                    }
                });
                if(nsc.isAllowNoChoice()) {
                    count.put("measurement.no.value", 0);
                }
            }
            if (col instanceof OrdinalScaledCriterion) {
                OrdinalScaledCriterion osc = (OrdinalScaledCriterion) col;
                List<OrdinalValue> values = osc.getValues();
                values.stream().forEach(val -> {
                    if (count.containsKey(val.getValue()) == false) {
                        count.put(val.getValue(), 0);
                    }
                });
                if(osc.isAllowNoChoice()) {
                    count.put("measurement.no.value", 0);
                }
            }
        });

        int i = getColumns().indexOf(getCriterion());
        if (i != -1) {
            getRows().forEach(row -> {
                Object item = row.get(i);
                if (item instanceof List) {
                    List<MeasurementValue> values = (List) item;
                    for(MeasurementValue mv : values) {
                        String value = mv.getValue();
                        if (count.containsKey(value)) {
                            Integer integer = count.get(value);
                            count.put(value, integer++);
                        }
                    }
                }
            });
        }
    }

    public Map<String, Integer> getCount() {
        return count;
    }

    @Override
    public AmSerialChart getResult() {
        AmSerialChart chart = new Chart().getChart();

        for (Map.Entry<String, Integer> entry : getCount().entrySet()) {
            Map<String, Object> data = new HashMap<>();
            data.put("choice", entry.getKey());
            data.put("count", entry.getValue());

            if (chart.getDataProvider() == null) {
                chart.setDataProvider(new ArrayList<>());
            }
            chart.getDataProvider().add(data);
        }

        return chart;
    }

    class Chart {
        public AmSerialChart getChart() {
            AmSerialChart chart = new AmSerialChart();
            chart.setType("serial");
            chart.setTheme("light");
            chart.setValueAxes(getValueAxes());
            chart.setGridAboveGraphs(true);
            chart.setStartDuration(1);
            chart.setGraphs(getGraphs());
            chart.setChartCursor(getChartCursor());
            chart.setCategoryField("choice");
            chart.setCategoryAxis(getCategoryAxis());
            return chart;
        }

        private CategoryAxis getCategoryAxis() {
            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.setGridPosition("start");
            categoryAxis.setGridAlpha(0);
            categoryAxis.setAutoWrap(true);
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
            List<AmGraph> list = new ArrayList<>();

            AmGraph amGraph = new AmGraph();
            amGraph.setBalloonText("[[choice]]: <b>[[count]]</b>");
            amGraph.setFillAlphas(0.8);
            amGraph.setLineAlpha(0.2);
            amGraph.setType("column");
            amGraph.setValueField("count");
            list.add(amGraph);

            return list;
        }

        private List<ValueAxis> getValueAxes() {
            List<ValueAxis> list = new ArrayList<>();

            ValueAxis valueAxis = new ValueAxis();
            valueAxis.setGridColor(Color.WHITE);
            valueAxis.setIntegersOnly(true);
            valueAxis.setLabelRotation(45);
            list.add(valueAxis);

            return list;
        }
    }
}