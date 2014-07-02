package de.stekoe.idss.reports;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;

@Ignore
public class ReportTest {

    private Project project = new Project();
    private TestReport report;
    private CriterionPage criterionPage;

    @Before
    public void setUp() {
        project = TestFactory.createProjectWithTeam(2);
        report = new TestReport();
        criterionPage = new CriterionPage();
        criterionPage.setProject(project);
    }

    @Test
    public void getColumnsOrdinalScaledCriterion() throws Exception {
        OrdinalScaledCriterion criterion = new OrdinalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        criterion.getValues().add(new OrdinalValue("A"));
        criterion.getValues().add(new OrdinalValue("B"));

        report.setCriterion(criterion);

        List<Object> actual = report.getColumns();

        List<Object> expected = new ArrayList<Object>();
        expected.add("id");
        expected.add("username");
        expected.add(criterion);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void getColumnsNominalScaledCriterionSingleChoice() throws Exception {
        NominalScaledCriterion criterion = new NominalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        NominalValue value1 = new NominalValue("D");
        criterion.getValues().add(value1);
        NominalValue value2 = new NominalValue("E");
        criterion.getValues().add(value2);
        NominalValue value3 = new NominalValue("F");
        criterion.getValues().add(value3);

        report.setCriterion(criterion);

        List<Object> actual = report.getColumns();

        List<Object> expected = new ArrayList<Object>();
        expected.add("id");
        expected.add("username");
        expected.add(criterion);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void getColumnsNominalScaledCriterionMultipleChoice() throws Exception {
        NominalScaledCriterion criterion = new NominalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        criterion.setMultipleChoice(true);
        NominalValue value1 = new NominalValue("D");
        criterion.getValues().add(value1);
        NominalValue value2 = new NominalValue("E");
        criterion.getValues().add(value2);
        NominalValue value3 = new NominalValue("F");
        criterion.getValues().add(value3);

        List<Criterion> list = new ArrayList<Criterion>();
        list.add(criterion);

        report.setCriterion(criterion);

        List<Object> actual = report.getColumns();

        List<Object> expected = new ArrayList<Object>();
        expected.add("id");
        expected.add("username");
        expected.add(value1);
        expected.add(value2);
        expected.add(value3);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void getRowsNominalScaledCriterionSingleChoice() throws Exception {
        NominalScaledCriterion criterion = new NominalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        criterion.getValues().add(new NominalValue("männlich"));
        criterion.getValues().add(new NominalValue("weiblich"));

        report.setCriterion(criterion);

        List<List<Object>> actual = report.getRows();
        assertThat(actual.size(), equalTo(report.getProject().getProjectTeam().size()));
        assertTrue(actual.get(0).contains(criterion.getValues().get(0)));
        assertFalse(actual.get(0).contains(criterion.getValues().get(1)));
    }

    @Test
    public void getRowsNominalScaledCriterionMultipleChoice() throws Exception {
        NominalScaledCriterion criterion = new NominalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        criterion.setMultipleChoice(true);
        criterion.getValues().add(new NominalValue("D"));
        criterion.getValues().add(new NominalValue("E"));
        criterion.getValues().add(new NominalValue("F"));

        report.setCriterion(criterion);

        List<List<Object>> actual = report.getRows();
        assertThat(actual.size(), equalTo(report.getProject().getProjectTeam().size()));
        assertTrue(actual.get(0).contains(criterion.getValues().get(0)));
    }

    @Test
    public void getRowsOrdinalScaledCriterion() throws Exception {
        OrdinalScaledCriterion criterion = new OrdinalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        criterion.getValues().add(new OrdinalValue("A"));
        criterion.getValues().add(new OrdinalValue("B"));

        report.setCriterion(criterion);

        List<List<Object>> actual = report.getRows();
        assertThat(actual.size(), equalTo(report.getProject().getProjectTeam().size()));
        assertTrue(actual.get(0).contains(criterion.getValues().get(0)));
    }

    @Test
    public void getRowsForNullUserChoice() throws Exception {
        OrdinalScaledCriterion criterion = new OrdinalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        criterion.setName("no choices made");
        criterion.getValues().add(new OrdinalValue("A"));
        criterion.getValues().add(new OrdinalValue("B"));

        report.setCriterion(criterion);

        List<List<Object>> actual = report.getRows();
        assertThat(actual.size(), equalTo(report.getProject().getProjectTeam().size()));
    }

    @Test
    public void getColumnForValueInRowTest() throws Exception {
        OrdinalScaledCriterion criterion = new OrdinalScaledCriterion();
        criterion.setCriterionPage(criterionPage);
        OrdinalValue value = new OrdinalValue("A");
        criterion.getValues().add(value);
        criterion.getValues().add(new OrdinalValue("B"));
        criterion.getValues().add(new OrdinalValue("C"));

        report.setCriterion(criterion);

        List<List<Object>> rows = report.getRows();
        List<Object> row = rows.get(0);
        Object column = report.getColumnForValueInRow(row, value);
        assertThat(column, instanceOf(Criterion.class));
        assertThat((Criterion)column, equalTo((Criterion)criterion));
    }

    public class TestReport extends Report {
        @Override
        UserChoice getUserChoiceForCriterion(User user, Criterion criterion) {
            SingleScaledCriterion<? extends MeasurementValue> ssc = (SingleScaledCriterion<? extends MeasurementValue>) criterion;
            if("no choices made".equals(ssc.getName())) {
                return null;
            }

            UserChoice userChoice = new UserChoice();
            userChoice.setCriterion(ssc);
            List<MeasurementValue> measurementValues = new ArrayList<MeasurementValue>();
            measurementValues.add(ssc.getValues().get(0));
            userChoice.setMeasurementValues(measurementValues);
            userChoice.setUser(user);
            return userChoice;
        }

        @Override
        public Object getResult() {
            return null;
        }

        @Override
        protected void run() {
        }
    }
}
