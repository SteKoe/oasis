package de.stekoe.idss.reports;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.stekoe.idss.model.OrdinalValue;


public class OrdinalChartReportTest {

    @Test
    public void medianTest1() throws Exception {
        OrdinalChartReport report = new OrdinalChartReport() {
            @Override
            List<Integer> getValuesAsFlatList() {
                List<Integer> values = new ArrayList<Integer>();

                values.add(1);
                values.add(1);
                values.add(1);
                values.add(1);
                values.add(2);
                values.add(3);
                values.add(4);

                return values;
            }
        };

        assertThat(report.calculateMedian(), equalTo(1));
    }

    @Test
    public void medianTest2() throws Exception {
        OrdinalChartReport report = new OrdinalChartReport() {
            @Override
            List<Integer> getValuesAsFlatList() {
                List<Integer> values = new ArrayList<Integer>();

                values.add(2);
                values.add(2);
                values.add(2);
                values.add(3);
                values.add(4);
                values.add(4);

                return values;
            }
        };

        assertThat(report.calculateMedian(), equalTo(3));
    }

    @Test
    public void medianTest3() throws Exception {
        OrdinalChartReport report = new OrdinalChartReport() {
            @Override
            List<Integer> getValuesAsFlatList() {
                List<Integer> values = new ArrayList<Integer>();

                values.add(2);
                values.add(99);

                return values;
            }
        };

        assertThat(report.calculateMedian(), equalTo(99));
    }

    @Test
    public void standardDeviation() throws Exception {
        OrdinalChartReport report = new OrdinalChartReport() {
            @Override
            Map<OrdinalValue, Integer> getOccurrences() {
                Map<OrdinalValue, Integer> occurrences = new LinkedHashMap<OrdinalValue, Integer>();

                OrdinalValue value = new OrdinalValue();
                value.setOrdering(52);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(60);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(48);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(66);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(54);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(62);
                occurrences.put(value, 1);

                return occurrences;
            }
        };

        assertThat(report.calculateArithmeticMean(), equalTo(58d));
        assertThat(report.calculateVariance(), equalTo(38.3));
        assertThat(report.calculateStandardDeviation(), equalTo(6.19139));
    }

    @Test
    public void standardDeviation2() throws Exception {
        OrdinalChartReport report = new OrdinalChartReport() {
            @Override
            Map<OrdinalValue, Integer> getOccurrences() {
                Map<OrdinalValue, Integer> occurrences = new LinkedHashMap<OrdinalValue, Integer>();

                OrdinalValue value = new OrdinalValue();
                value.setOrdering(0);
                occurrences.put(value, 4);

                value = new OrdinalValue();
                value.setOrdering(1);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(2);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setOrdering(3);
                occurrences.put(value, 1);

                return occurrences;
            }
        };

        assertThat("Median mean error!", report.calculateMedian(), equalTo(0));
        assertThat("Arithmetic mean error!", round(report.calculateArithmeticMean(), 2), equalTo(1.86));
        assertThat("Variance error!", round(report.calculateVariance(), 4), equalTo(1.2653));
        assertThat("Standard deviation error!", round(report.calculateStandardDeviation(), 4), equalTo(1.1249));
    }

    private double round(double number, int digits) {
        double d = Math.pow(10, digits);
        double result = Math.round(number * d) / d;
        return result;
    }
}
