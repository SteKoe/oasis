package de.stekoe.idss.page.project.criterion;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.Quote;
import org.apache.log4j.Logger;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;

public class CSVReport {
    private static final Logger LOG = Logger.getLogger(CSVReport.class);

    private UserChoiceRepository userChoiceRepository;
    private final CSVPrinter csvPrinter = new CSVPrinter(getPrintStream(), getCSVFormat());
    private String csvHeader;
    private String csvBody;
    private Project project;
    private List<Criterion> criterionList;

    public void setUserChoiceRepository(UserChoiceRepository userChoiceRepository) {
        this.userChoiceRepository = userChoiceRepository;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }

    public String run() {
        criterionList = getProject().getScaleList();

        writeCsvHeader();
        writeCsvBody();

        return toString();
    }

    private void writeCsvHeader() {
        List<String> headerValues = new ArrayList<String>();
        headerValues.add("id");
        headerValues.add("username");

        for (Criterion criterion : this.criterionList) {

            List<? extends MeasurementValue> values = new ArrayList<MeasurementValue>();

            if(criterion instanceof NominalScaledCriterion) {
                NominalScaledCriterion nominalScaledCriterion = (NominalScaledCriterion) criterion;
                values = nominalScaledCriterion.getValues();
            } else if(criterion instanceof OrdinalScaledCriterion) {
                OrdinalScaledCriterion ordinalScaledCriterion = (OrdinalScaledCriterion) criterion;
                values = ordinalScaledCriterion.getValues();
            }

            for(MeasurementValue value : values) {
                headerValues.add(String.format("%s_%s_%s", getCsvHeaderQuestionPrefix(), criterion.getOrdering(), value.getOrdering()));
            }
        }

        try {
            StringWriter out = new StringWriter();
            CSVPrinter printer = new CSVPrinter(out, getCSVFormat());
            printer.printRecord(headerValues);
            printer.close();
            out.close();

            setCsvHeader(out.toString());

        } catch(IOException e) {
            LOG.error("Failed to create csv header.", e);
        }
    }

    private void writeCsvBody() {
        try {
            StringWriter out = new StringWriter();

            Set<ProjectMember> projectTeam = getProject().getProjectTeam();
            for (ProjectMember projectMember : projectTeam) {

                CSVPrinter printer = new CSVPrinter(out, getCSVFormat());

                List<Object> record = new ArrayList<Object>();
                record.add(projectMember.getUser().getId());
                record.add(projectMember.getUser().getUsername());

                for(Criterion criterion : criterionList) {
                    UserChoice userChoice = getUserChoiceForCriterion(projectMember.getUser(), criterion);
                    if(criterion instanceof SingleScaledCriterion) {
                        List<? extends MeasurementValue> values = ((SingleScaledCriterion) criterion).getValues();
                        for (MeasurementValue measurementValue : values) {
                            if(userChoice != null && userChoice.getMeasurementValues() != null && userChoice.getMeasurementValues().contains(measurementValue)) {
                                record.add(1);
                            } else {
                                record.add(0);
                            }
                        }
                    }
                }

                printer.printRecord(record);
                printer.close();
            }
            out.close();

            setCsvBody(out.toString());
        } catch(IOException e) {
            LOG.error(e);
        }
    }

    UserChoice getUserChoiceForCriterion(User user, Criterion criterion) {
        return userChoiceRepository.findByUserAndCriterion(user.getId(), criterion.getId());
    }

    private String getCsvHeaderQuestionPrefix() {
        return "Question";
    }

    PrintStream getPrintStream() {
        return System.out;
    }

    CSVFormat getCSVFormat() {
        return CSVFormat.RFC4180.withQuotePolicy(Quote.ALL);
    }

    public CSVPrinter getCsvPrinter() {
        return csvPrinter;
    }

    public String getCsvHeader() {
        return csvHeader;
    }
    private void setCsvHeader(String csvHeader) {
        this.csvHeader = csvHeader;
    }

    public String getCsvBody() {
        return this.csvBody;
    }
    private void setCsvBody(String csvBody) {
        this.csvBody = csvBody;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCsvHeader());
        sb.append(getCsvBody());
        return sb.toString();
    }
}
