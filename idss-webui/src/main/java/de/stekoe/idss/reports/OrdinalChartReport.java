package de.stekoe.idss.reports;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import de.stekoe.amcharts.AmGraph;
import de.stekoe.amcharts.AmSerialChart;
import de.stekoe.amcharts.CategoryAxis;
import de.stekoe.amcharts.ChartCursor;
import de.stekoe.amcharts.Label;
import de.stekoe.amcharts.ValueAxis;
import de.stekoe.amcharts.addition.Color;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.session.WebSession;


public class OrdinalChartReport extends Report<AmSerialChart> {

    private final Map<OrdinalValue, Integer> occurrences = new LinkedHashMap<OrdinalValue, Integer>();

    private SingleScaledCriterion<OrdinalValue> criterion;
    private AmSerialChart chart;

    @Override
    protected void run() {
        Criterion criterion = getCriterion();
        if(!(criterion instanceof SingleScaledCriterion))
            return;

        this.criterion = (SingleScaledCriterion<OrdinalValue>) criterion;

        runCalculation();

        chart = Chart.getChart();
        for(Entry<OrdinalValue, Integer> entry : getOccurrences().entrySet()) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("choice", entry.getKey().getValue());
            data.put("count", entry.getValue());
            chart.getDataProvider().add(data);
        }

        chart.setAllLabels(new ArrayList<Label>());
        int y = 0;
        List<Label> label = getLabel();
        for(int i = 0; i < label.size(); i++) {
            Label l = label.get(i);
            if(i%2 == 0) {
                l.setAlign("right");
                l.setX("89%");
                l.setY(String.valueOf(y += 15));
            } else {
                l.setAlign("left");
                l.setX("90%");
                l.setY(String.valueOf(y));
            }
            chart.getAllLabels().add(l);
        }
    }

    private String numberFormat(double number) {
        Locale locale  = WebSession.get().getLocale();
        DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern("######.##");

        return decimalFormat.format(number);
    }

    List<Label> getLabel() {
        ArrayList<Label> list = new ArrayList<Label>();

        // Sample Size
        Label label = new Label();
        label.setText(ChartUtils.getString("label.sampleSize") + ": ");
        list.add(label);
        label = new Label();
        label.setText(numberFormat(calculateSampleSize()));
        list.add(label);

        // Arithmetic Mean
        label = new Label();
        label.setText(ChartUtils.getString("label.mean.arithmetic") + ": ");
        list.add(label);
        label = new Label();
        label.setText(numberFormat(calculateArithmeticMean()));
        list.add(label);

        // Standard deviation
        label = new Label();
        label.setText(ChartUtils.getString("label.standardDeviation") + ": ");
        list.add(label);
        label = new Label();
        label.setText(numberFormat(calculateStandardDeviation()));
        list.add(label);

        // Median
        label = new Label();
        label.setText(ChartUtils.getString("label.median") + ": ");
        list.add(label);
        label = new Label();
        label.setText(numberFormat(calculateMedian()));
        list.add(label);

        return list;
    }

    private double calculateSampleSize() {
        double sampleSize = 0.0;
        for(Entry<OrdinalValue, Integer> entry : getOccurrences().entrySet()) {
            sampleSize += entry.getValue();
        }

        return sampleSize;
    }

    double calculateArithmeticMean() {
        double sum = 0;

        for(Entry<OrdinalValue, Integer> entry : getOccurrences().entrySet()) {
            OrdinalValue mv = entry.getKey();
            int occurrences = entry.getValue();
            sum += occurrences * (mv.getOrdering() + 1);
        }

        double calculateSampleSize = calculateSampleSize();
        double result = sum/calculateSampleSize;
        return result;
    }

    int calculateMedian() {
        List<Integer> median = getValuesAsFlatList();
        double half = Math.floor(median.size()/2.0);
        return median.get((int) half);
    }

    double calculateVariance() {
        double arithmeticMean = calculateArithmeticMean();

        double deviation = 0.0;
        for(Entry<OrdinalValue, Integer> entry : getOccurrences().entrySet()) {
            OrdinalValue mv = entry.getKey();
            int value = mv.getOrdering() + 1;

            for(int i = 0; i < entry.getValue(); i++) {
                deviation += Math.pow(value - arithmeticMean, 2);
            }
        }

        double n = calculateSampleSize();
        if(n > 100) {
            return deviation/(n-1L);
        } else {
            return deviation/n;
        }
    }

    double calculateStandardDeviation() {
        return Math.sqrt(calculateVariance());
    }

    List<Integer> getValuesAsFlatList() {
        List<Integer> median = new ArrayList<Integer>();

        for(Entry<OrdinalValue, Integer> entry : getOccurrences().entrySet()) {
            OrdinalValue mv = entry.getKey();
            int occurrences = entry.getValue();

            for(int i = 0; i < occurrences; i++) {
                median.add(mv.getOrdering() + 1);
            }
        }

        Collections.sort(median);
        return median;
    }

    /**
     * Runs all necessary calculations for this chart report
     */
    private void runCalculation() {
        initOccurrencesMap();

        List<List<Object>> rows = getRows();
        for (List<Object> row : rows) {
            for(Object value : row) {
                if(value instanceof MeasurementValue && getOccurrences().containsKey(value)) {
                    int count = getOccurrences().get(value);
                    getOccurrences().put((OrdinalValue)value, new Integer(count + 1));
                }
            }
        }

        return;
    }

    /**
     * Creates an entry for each available MeasurementValue in the occurences Map with initial value 0.
     */
    private void initOccurrencesMap() {
        for(OrdinalValue mv : criterion.getValues()) {
            getOccurrences().put(mv, 0);
        }
    }

    public Criterion getCriterion() {
        if(getCriterions().size() == 1) {
            return getCriterions().get(0);
        } else {
            return null;
        }
    }

    @Override
    public AmSerialChart getResult() {
        return chart;
    }

    Map<OrdinalValue, Integer> getOccurrences() {
        return occurrences;
    }

    static class Chart {
        private Chart() {}

        public static AmSerialChart getChart() {
            return new Chart().get();
        }

        private AmSerialChart get() {
            AmSerialChart chart = new AmSerialChart();
            chart.setMarginTop(60);
            chart.setType("serial");
            chart.setTheme("light");
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
            categoryAxis.setTitle(ChartUtils.getString("label.axis.measurementvalues"));
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

            return list;
        }

        private List<ValueAxis> getValueAxes() {
            List<ValueAxis> list = new ArrayList<ValueAxis>();

            ValueAxis valueAxis = new ValueAxis();
            valueAxis.setId("countAxis");
            valueAxis.setGridColor(Color.WHITE);
            valueAxis.setTitle(ChartUtils.getString("label.axis.count"));
            valueAxis.setIntegersOnly(true);
            list.add(valueAxis);

            valueAxis = new ValueAxis();

            return list;
        }
    }
}

