package de.stekoe.idss;

import java.io.File;

import org.junit.Test;


public class XmlExportTest {
    @Test
    public void testName() throws Exception {
        File outputFile = new File("src/test/resources/refCriterion.xml");
        XmlExport xmlExport = new XmlExport();
        xmlExport.setFile(outputFile);

        xmlExport.export();
    }
}
