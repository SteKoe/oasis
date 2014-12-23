package de.stekoe.oasis.web.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.stekoe.amcharts.AmChart;
import de.stekoe.amcharts.AmSerialChart;
import de.stekoe.amcharts.addition.ColorSerialiser;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.UserChoices;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.EmployeeService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserChoicesService;
import de.stekoe.oasis.web.reports.CSVReport;
import de.stekoe.oasis.web.reports.OccurrencesChartReport;
import de.stekoe.oasis.web.reports.RadarChartReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@Controller
public class EvaluationResultController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionService criterionService;

    @Autowired
    UserChoicesService userChoicesService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/project/{pid}/evaluation/result", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, @PathVariable String pid, Locale locale) {
        Project project = projectService.findOne(pid);
        List<Criterion> criterions = criterionService.findAllForReport(project.getId());
        List<UserChoices> userChoices = userChoicesService.findByProject(pid);

        Map<Criterion, Map<String, String>> criterionCharts = new LinkedHashMap<>();
        for(Criterion criterion : criterions) {
            Map<String, String> charts = new LinkedHashMap<>();

            if(criterion instanceof SingleScaledCriterion) {
                OccurrencesChartReport chart = new OccurrencesChartReport(criterion, userChoices);
                chart.run();
                AmChart result = chart.getResult();
                chartDataProviderLocalizer(locale, result);
                charts.put("label.chart.occurrences", getGsonBuilder().toJson(result));

                if (((SingleScaledCriterion)criterion).getValues().size() >= 3) {
                    RadarChartReport radarChartReport = new RadarChartReport(criterion, userChoices, employeeService);
                    radarChartReport.run();
                    result = radarChartReport.getResult();
                    chartDataProviderLocalizer(locale, result);
                    charts.put("label.chart.radarchart", getGsonBuilder().toJson(result));
                }

                criterionCharts.put(criterion, charts);
            }
        }

        ModelAndView model = new ModelAndView("/project/evaluation/result/list");
        model.addObject("project", project);
        model.addObject("pageTitle", project.getName());
        model.addObject("criterionCharts", criterionCharts);
        return model;
    }

    private void chartDataProviderLocalizer(Locale locale, AmChart result) {
        result.getDataProvider().forEach(data -> {
            if(data instanceof Map) {
                Map dataMap = (Map) data;
                Set keys = dataMap.keySet();
                for(Object key : keys) {
                    Object val = dataMap.get(key);

                    if(val instanceof String) {
                        String localizedVal = messageSource.getMessage((String) val, null, locale);
                        dataMap.put(key, localizedVal);
                    }
                }
            }
        });
    }

    private Gson getGsonBuilder() {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Color.class, new ColorSerialiser());
        return gsonBuilder.create();
    }

    @RequestMapping(value = "/project/{pid}/evaluation/result/csv", method = RequestMethod.GET)
    public void downloadResultsAsCSV(@PathVariable String pid, HttpServletResponse response) {
        Project project = projectService.findOne(pid);
        List<Criterion> criterions = criterionService.findAllForReport(project.getId());
        List<UserChoices> userChoices = userChoicesService.findByProject(pid);

        CSVReport csvReport = new CSVReport(criterions, userChoices);
        csvReport.run();
        try {
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", project.getName().toLowerCase().replaceAll(" ","_") + "_results.csv");
            response.setHeader(headerKey, headerValue);

            InputStream data = new ByteArrayInputStream(csvReport.getResult().getBytes());
            ServletOutputStream outputStream = response.getOutputStream();
            FileCopyUtils.copy(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
