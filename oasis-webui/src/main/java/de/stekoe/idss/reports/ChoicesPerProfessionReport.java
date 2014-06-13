package de.stekoe.idss.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.stekoe.amcharts.AmGraph;
import de.stekoe.amcharts.AmLegend;
import de.stekoe.amcharts.AmRadarChart;
import de.stekoe.amcharts.ValueAxis;
import de.stekoe.amcharts.addition.Color;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.UserChoice;

public class ChoicesPerProfessionReport extends Report<AmRadarChart> {

    private final Set<String> professions = new HashSet<String>();
    private final Map<String, Map<MeasurementValue, Integer>> results = new HashMap<String, Map<MeasurementValue, Integer>>();
    private AmRadarChart amRadarChart;

    /**
     * Gruppe A => MV1, 1
     *          => MV2, 22
     */

    @Override
    protected void run() {
        List<List<Object>> rows = getRows();

        computeListOfProfessions();

        for(List<Object> row : rows) {
            ProjectMember pm = (ProjectMember) row.get(2);
            String profession = pm.getUser().getProfessional();

            SingleScaledCriterion<MeasurementValue> criterion = (SingleScaledCriterion<MeasurementValue>) getCriterion();
            Iterator<MeasurementValue> valuesIterator = criterion.getValues().iterator();
            while(valuesIterator.hasNext()) {
                MeasurementValue value = valuesIterator.next();
                if(!results.get(profession).containsKey(value)) {
                    results.get(profession).put(value, 0);
                }
            }

            UserChoice userChoiceForCriterion = getUserChoiceForCriterion(pm.getUser(), getCriterion());
            if(userChoiceForCriterion != null) {
                Map<MeasurementValue, Integer> resultsForProfession = results.get(profession);
                if(resultsForProfession != null) {
                    for(MeasurementValue mv : userChoiceForCriterion.getMeasurementValues()) {
                        int currentCount = resultsForProfession.get(mv);
                        resultsForProfession.put(mv, currentCount+1);
                    }
                }
            }
        }

        amRadarChart = new AmRadarChart();
        amRadarChart.setType("radar");
        amRadarChart.setTheme("light");
        amRadarChart.setStartDuration(0d);
        amRadarChart.setCategoryField("value");
        amRadarChart.setDataProvider(getDataProvider());
        amRadarChart.setGraphs(getGraphs());
        amRadarChart.setValueAxes(getValueAxes());
        amRadarChart.setLegend(getLegend());
    }

    private AmLegend getLegend() {
        AmLegend legend = new AmLegend();
        legend.setUseGraphSettings(true);
        legend.setAlign("center");
        return legend;
    }

    private List<ValueAxis> getValueAxes() {
        List<ValueAxis> valueAxes = new ArrayList<ValueAxis>();
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
        List<AmGraph> graphs = new ArrayList<AmGraph>();
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
        List<Object> dataProvider = new ArrayList<Object>();
        // iterate
        for(MeasurementValue mv : ((SingleScaledCriterion<MeasurementValue>)getCriterion()).getValues()) {
            Map<String, Object> data = new HashMap<String, Object>();
            for(String profession : professions) {
                data.put("value", mv.getValue());
                int resultForProfession = results.get(profession).get(mv);
                data.put(profession, resultForProfession);
            }
            dataProvider.add(data);
        }
        return dataProvider;
    }

    private void computeListOfProfessions() {
        Project project = getProject();
        Set<ProjectMember> projectTeam = project.getProjectTeam();
        Iterator<ProjectMember> projectTeamIterator = projectTeam.iterator();
        while(projectTeamIterator.hasNext()) {
            ProjectMember pm = projectTeamIterator.next();
            String profession = pm.getUser().getProfessional();
            professions.add(profession);
            results.put(profession, new HashMap<MeasurementValue, Integer>());
        }
    }

    @Override
    public AmRadarChart getResult() {
        return amRadarChart;
    }

}
