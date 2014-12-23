package de.stekoe.oasis.web.reports;

import de.stekoe.idss.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CSVReport extends MultiCriterionReport<String> {
    private static final Logger LOG = Logger.getLogger(CSVReport.class);

    private CSVPrinter csvPrinter;
    private String result;

    public CSVReport(List<Criterion> criterions, List<UserChoices> userChoices) {
        super(criterions, userChoices);
    }

    @Override
    public void run() {
        try {
            csvPrinter = new CSVPrinter(getPrintStream(), getCSVFormat());
        } catch (Exception e) {
            LOG.error("An execption occured during CSV Report!", e);
        }

        this.result = getCsvHeader() + getCsvBody();
    }

    @Override
    public String getResult() {
        return result;
    }

    private String getCsvHeader() {
        String output = "";
        try {
            List<Object> columns = new ArrayList<>();
            for(Object column : getColumns()) {
                String value = "-";
                if(column instanceof SingleScaledCriterion) {

                    if(column instanceof NominalScaledCriterion) {
                        NominalScaledCriterion nsc = (NominalScaledCriterion) column;
                        if(nsc.isMultipleChoice()) {
                            nsc.getValues().stream().forEach(val -> {
                                columns.add(val.getValue());
                            });

                            if(nsc.isAllowNoChoice()) {
                                columns.add("nc");
                            }
                            continue;
                        } else {
                            value = ((SingleScaledCriterion) column).getName();
                        }
                    } else {
                        value = ((SingleScaledCriterion) column).getName();
                    }
                } else if (column.equals("projectMember")) {
                    continue;
                } else if (column instanceof String) {
                    value = (String) column;
                }
                columns.add(value);
            }

            StringWriter out = new StringWriter();
            CSVPrinter printer = new CSVPrinter(out, getCSVFormat());
            printer.printRecord(columns);
            printer.close();
            out.close();

            output = out.toString();
        } catch(IOException e) {
            LOG.error("Failed to create csv header.", e);
        }

        return output;
    }

    private String getCsvBody() {
        String output = "";

        try {
            StringWriter out = new StringWriter();
            CSVPrinter printer = new CSVPrinter(out, getCSVFormat());
            List<List<Object>> rows = getRows();
            for(List<Object> row : rows) {
                List<Object> rowData = new ArrayList<>();
                for(int i = 0; i < row.size(); i++) {
                    Object data = row.get(i);

                    Object columnType = getColumns().get(i);
                    if (columnType instanceof NominalScaledCriterion) {
                        List<NominalValue> list = (List<NominalValue>) data;

                        if (list.size() == 0) {
                            rowData.add("");
                        } else {
                            NominalScaledCriterion criterion = (NominalScaledCriterion) columnType;
                            if (criterion.isMultipleChoice()) {
                                criterion.getValues().stream().forEach(val -> {
                                    if (list.contains(val)) {
                                        rowData.add(1);
                                    } else {
                                        rowData.add(0);
                                    }
                                });

                                if (criterion.isAllowNoChoice()) {
                                    rowData.add(getValueForNoChoice(list));
                                }
                            } else {
                                rowData.add(list.get(0).getValue());
                            }
                        }
                    } else if (columnType instanceof OrdinalScaledCriterion) {
                        List<OrdinalValue> list = (List<OrdinalValue>) data;

                        if (list.size() == 0) {
                            rowData.add("");
                        } else {
                            OrdinalScaledCriterion criterion = (OrdinalScaledCriterion) columnType;
                            rowData.add(list.get(0).getValue());
                        }
                    } else if (columnType.equals("projectMember")) {
                        continue;
                    } else {
                        rowData.add(data);
                    }
                }
                printer.printRecord(rowData);
            }
            printer.close();
            out.close();

            output = out.toString();
        } catch(IOException e) {
            LOG.error("Failed to write rows to csv.", e);
        }

        return output;
    }

    private String getValueForNoChoice(List<? extends MeasurementValue> list) {
        Optional<MeasurementValue> value = list.stream()
                .map(mv -> (MeasurementValue) mv)
                .filter(_val -> _val.getValue().equals("measurement.no.value"))
                .findAny();

        String val;
        if(value.isPresent()) {
            val = value.get().getValue();
        } else {
            val = "";
        }
        return val;
    }

    PrintStream getPrintStream() {
        return System.out;
    }

    CSVFormat getCSVFormat() {
        return CSVFormat.RFC4180.withQuoteMode(QuoteMode.ALL);
    }

    public CSVPrinter getCsvPrinter() {
        return csvPrinter;
    }
}
