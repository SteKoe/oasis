package de.stekoe.idss.reports;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.Quote;
import org.apache.log4j.Logger;

import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.SingleScaledCriterion;

public class CSVReport extends Report<String> {
    private static final Logger LOG = Logger.getLogger(CSVReport.class);

    private final CSVPrinter csvPrinter = new CSVPrinter(getPrintStream(), getCSVFormat());
    private String result;

    @Override
    protected void run() {
        this.result = getCsvHeader() + getCsvBody();
    }

    @Override
    public String getResult() {
        return result;
    }

    private String getCsvHeader() {
        String output = "";

        try {
            List<Object> columns = new ArrayList<Object>();
            for(Object column : getColumns()) {
                String value = null;
                if(column instanceof SingleScaledCriterion) {
                    value = ((SingleScaledCriterion) column).getName();
                } else if(column instanceof MeasurementValue) {
                    value = ((MeasurementValue) column).getValue();
                } else if(column instanceof String) {
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
                List<Object> rowData = new ArrayList<Object>();
                for(int i = 0; i < row.size(); i++) {
                    Object columnType = getColumns().get(i);
                    Object data = row.get(i);
                    if(columnType instanceof MeasurementValue) {
                        if(data == null) {
                            rowData.add(0);
                        } else {
                            rowData.add(1);
                        }
                    } else if(columnType instanceof SingleScaledCriterion) {
                        if(data instanceof MeasurementValue) {
                            rowData.add(((MeasurementValue) data).getValue());
                        } else {
                            rowData.add("");
                        }
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

    PrintStream getPrintStream() {
        return System.out;
    }

    CSVFormat getCSVFormat() {
        return CSVFormat.RFC4180.withQuotePolicy(Quote.ALL);
    }

    public CSVPrinter getCsvPrinter() {
        return csvPrinter;
    }
}
