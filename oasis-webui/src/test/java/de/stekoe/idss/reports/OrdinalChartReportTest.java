package de.stekoe.idss.reports;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;


public class OrdinalChartReportTest {

    private boolean test;
    @Test
    public void testName() throws Exception {
        assertFalse(test);
    }

    private int[] a;
    @Test
    public void intArray() throws Exception {
        assertThat(a, is(nullValue()));
    }

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

                OrdinalScaledCriterion osc = new OrdinalScaledCriterion();

                OrdinalValue value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                return occurrences;
            }
        };

        assertThat(report.calculateArithmeticMean(), equalTo(3.5d));
        assertThat(round(report.calculateVariance(),4), equalTo(2.9167));
        assertThat(round(report.calculateStandardDeviation(),4), equalTo(1.7078));
    }

    @Test
    public void standardDeviation2() throws Exception {
        OrdinalChartReport report = new OrdinalChartReport() {
            @Override
            Map<OrdinalValue, Integer> getOccurrences() {
                Map<OrdinalValue, Integer> occurrences = new LinkedHashMap<OrdinalValue, Integer>();

                OrdinalScaledCriterion osc = new OrdinalScaledCriterion();

                OrdinalValue value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 4);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                value = new OrdinalValue();
                value.setCriterion(osc);
                osc.getValues().add(value);
                occurrences.put(value, 1);

                return occurrences;
            }
        };

        assertThat("Median mean error!", report.calculateMedian(), equalTo(1));
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
