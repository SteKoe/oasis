package de.stekoe.idss;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.service.CriterionGroupService;

public class XmlImportTest extends AbstractWicketApplicationTester {

    @Inject
    CriterionGroupService criterionGroupService;

    @Test
    public void testName() throws Exception {
        File file = new File("src/test/resources/referenceCriterions.xml");
        XmlImport xml = new XmlImport(file);
        List<CriterionGroup> criterionGroups = xml.getCriterionGroups();
        assertThat(criterionGroups.size(), is(equalTo(2)));
        assertThat(criterionGroups.get(0).getCriterions().size(), is(equalTo(3)));
        assertThat(criterionGroups.get(1).getCriterions().size(), is(equalTo(1)));

        criterionGroupService.save(criterionGroups);
    }
}
