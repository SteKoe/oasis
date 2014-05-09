package de.stekoe.idss.page.project.criterion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;


public class CSVReportTest {

    private Project project = new Project();
    private TestCSVReport csvReport;

    @Before
    public void setUp() {
        project = TestFactory.createProjectWithTeam();
        project.getScaleList().addAll(getCriterionList());

        List<SingleScaledCriterion<? extends MeasurementValue>> criterionList = getCriterionList();

        UserChoiceRepository mock = mock(UserChoiceRepository.class);
        csvReport = new TestCSVReport();
        csvReport.setUserChoiceRepository(mock);
        csvReport.setProject(project);
        csvReport.run();

        csvReport.toString();
    }

    @Test
    public void getCsvHeaderTest() throws Exception {
        String csvHeader = csvReport.getCsvHeader();
        assertThat(csvHeader, equalTo("\"id\",\"username\",\"Question_0_0\",\"Question_0_1\",\"Question_1_0\",\"Question_1_1\",\"Question_1_2\"\r\n"));
    }

    @Test
    public void testName() throws Exception {
        String csvBody = csvReport.getCsvBody();
        assertThat(csvBody, equalTo("\"Question_0_0\",\"Question_0_1\",\"Question_1_0\",\"Question_1_1\",\"Question_1_2\"\r\n"));
    }

    private List<SingleScaledCriterion<? extends MeasurementValue>> getCriterionList() {
        List<SingleScaledCriterion<? extends MeasurementValue>> criterionList = new ArrayList<SingleScaledCriterion<? extends MeasurementValue>>();

        OrdinalScaledCriterion criterion = new OrdinalScaledCriterion();
        criterion.setOrdering(0);
        criterion.addValue(new OrdinalValue(1, "A"));
        criterion.addValue(new OrdinalValue(2, "B"));
        criterionList.add(criterion);

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setMultipleChoice(true);
        nsc.setOrdering(1);
        nsc.addValue(new NominalValue("D"));
        nsc.addValue(new NominalValue("E"));
        nsc.addValue(new NominalValue("F"));
        criterionList.add(nsc);
        return criterionList;
    }

    public class TestCSVReport extends CSVReport {
        @Override
        UserChoice getUserChoiceForCriterion(User user, Criterion criterion) {
            SingleScaledCriterion<? extends MeasurementValue> ssc = (SingleScaledCriterion<? extends MeasurementValue>) criterion;

            UserChoice userChoice = new UserChoice();
            userChoice.setCriterion(ssc);
            List<MeasurementValue> measurementValues = new ArrayList<MeasurementValue>();
            measurementValues.add(ssc.getValues().get(0));
            userChoice.setMeasurementValues(measurementValues);
            userChoice.setUser(user);
            return userChoice;
        }
    }
}
