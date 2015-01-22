package de.stekoe.oasis.web.reports;

import de.stekoe.amcharts.AmGraph;
import de.stekoe.amcharts.AmLegend;
import de.stekoe.amcharts.AmRadarChart;
import de.stekoe.amcharts.ValueAxis;
import de.stekoe.amcharts.addition.Color;
import de.stekoe.oasis.model.*;
import de.stekoe.oasis.service.EmployeeService;

import java.util.*;

public class RadarChartReport extends SingleCriterionReport<AmRadarChart> {

    private final EmployeeService employeeService;
    private List<String> professions = new ArrayList<>();
    private final Map<MeasurementValue, Map<String, Integer>> results = new HashMap<>();
    private final Map<User, String> userToProfession = new HashMap<>();

    public RadarChartReport(Criterion criterion, List<UserChoices> userChoiceses, EmployeeService employeeService) {
        super(criterion, userChoiceses);
        this.employeeService = employeeService;
    }

    @Override
    public void run() {
        prepareLists();

        getColumns().stream().forEach(col -> {
            if (col instanceof SingleScaledCriterion) {
                SingleScaledCriterion ssc = (SingleScaledCriterion) col;
                ssc.getValues().forEach(val -> {
                    Map<String, Integer> result = new HashMap<>();
                    professions.forEach(profession -> {
                        result.put(profession, 0);
                    });
                    results.put((MeasurementValue) val, result);
                });
                if (ssc.isAllowNoChoice()) {
                    //measurement.no.value
                    MeasurementValue mv = new MeasurementValue();
                    mv.setValue("measurement.no.value");

                    Map<String, Integer> result = new HashMap<>();
                    professions.forEach(profession -> {
                        result.put(profession, 0);
                    });
                    results.put(mv, result);
                }
            }
        });

        getRows().forEach(row -> {
            ProjectMember projectMember = (ProjectMember)row.get(2);

            for(int i = 3; i < row.size(); i++) {
                Object rowObject = row.get(i);
                if(rowObject instanceof List) {
                    List mvs = (List) rowObject;
                    mvs.forEach(mv -> {
                        if(mv instanceof MeasurementValue) {
                            String profession = userToProfession.get(projectMember.getUser());

                            if(results.containsKey(mv)) {
                                Map<String, Integer> stringIntegerMap = results.get(mv);
                                Integer count = stringIntegerMap.get(profession);

                                stringIntegerMap.put(profession, count+1);
                            } else if(((MeasurementValue)mv).getValue().equals("measurement.no.value")) {
                                MeasurementValue mmv = results.keySet().stream().filter(_mv -> _mv.getValue().equals("measurement.no.value")).findAny().get();
                                Map<String, Integer> stringIntegerMap = results.get(mmv);
                                Integer count = stringIntegerMap.get(profession);

                                stringIntegerMap.put(profession, count+1);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public AmRadarChart getResult() {
        return new Chart().getChart();
    }

    private void prepareLists() {
        getRows().stream().forEach(row -> {
            ProjectMember projectMember = (ProjectMember) row.get(2);

            User user = projectMember.getUser();
            Employee employee = employeeService.findByUser(user).get(0);
            String profession = employee.getRole().getName();

            professions.add(profession);
            userToProfession.put(user, profession);
        });
    }

    class Chart {
        public AmRadarChart getChart() {
            AmRadarChart amRadarChart = new AmRadarChart();
            amRadarChart.setType("radar");
            amRadarChart.setTheme("light");
            amRadarChart.setStartDuration(0d);
            amRadarChart.setCategoryField("value");
            amRadarChart.setDataProvider(getDataProvider());
            amRadarChart.setGraphs(getGraphs());
            amRadarChart.setValueAxes(getValueAxes());
            amRadarChart.setLegend(getLegend());
            return amRadarChart;
        }

        private AmLegend getLegend() {
            AmLegend legend = new AmLegend();
            legend.setUseGraphSettings(true);
            legend.setAlign("center");
            return legend;
        }

        private List<ValueAxis> getValueAxes() {
            List<ValueAxis> valueAxes = new ArrayList<>();
            ValueAxis valueAxis = new ValueAxis();
            valueAxis.setGridType("circles");
            valueAxis.setAutoGridCount(true);
            valueAxis.setAxisAlpha(0.2);
            valueAxis.setFillAlpha(0.05);
            valueAxis.setFillColor(Color.WHITE);
            valueAxis.setIntegersOnly(true);
            valueAxis.setGridAlpha(0.08);
            valueAxis.setPosition("left");
            valueAxes.add(valueAxis);
            return valueAxes;
        }

        private List<AmGraph> getGraphs() {
            List<AmGraph> graphs = new ArrayList<>();
            for(String profession : professions) {
                AmGraph graph = new AmGraph();
                graph.setBalloonText("[[value]]");
                graph.setValueField(profession);
                graph.setTitle(profession);
                graph.setFillAlphas(0.3);
                graph.setBullet("round");
                graphs.add(graph);
            }
            return graphs;
        }

        private List<Object> getDataProvider() {
            List<Object> dataProvider = new ArrayList<>();

            Set<MeasurementValue> mvs = results.keySet();
            for(MeasurementValue mv : mvs) {
                Map<String, Object> data = new HashMap<>();
                data.put("value", mv.getValue());

                for(String profession : professions) {
                    data.put(profession, results.get(mv).get(profession));
                }
                dataProvider.add(data);
            }

            return dataProvider;
        }
    }
}
