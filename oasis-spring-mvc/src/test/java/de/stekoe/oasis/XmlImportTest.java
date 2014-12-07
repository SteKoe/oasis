package de.stekoe.oasis;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionService;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class XmlImportTest extends AbstractWebAppTest {

    @Autowired
    CriterionGroupService criterionGroupService;

    @Autowired
    CriterionService criterionService;

    @Test
    public void xml() throws Exception {
        File file = new File("./oasis-spring-mvc/src/test/resources/referenceCriterions.xml");
        XmlImport xml = new XmlImport(file);
        List<CriterionGroup> criterionGroups = xml.getCriterionGroups();
        criterionGroupService.save(criterionGroups);
    }

    @Test
    public void sadfgh() throws Exception {
        CriterionGroup criterionGroup = new CriterionGroup();
        criterionGroup.setName("asd");

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("asd");

        criterionGroup.getCriterions().add(nsc);

        criterionGroupService.save(criterionGroup);
    }
}
