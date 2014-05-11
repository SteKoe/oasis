package de.stekoe.idss.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;

/**
 * This class computes a table which contains all Criterions, MeasurementValues and UserChoices for a given Project.
 */
public abstract class Report<T> implements Serializable {
    private UserChoiceRepository userChoiceRepository;
    private List<Object> columns;
    private List<List<Object>> rows;
    private Project project;
    private List<Criterion> criterionList;

    /**
     * Set a List of Criterions which should be part of report.
     * All Criterions have to be associated to the same project.
     *
     * @param criterions List of criterions
     */
    public void setCriterions(List<Criterion> criterions) {
        Project project = null;
        for(Criterion criterion : criterions) {
            CriterionPage criterionPage = criterion.getCriterionPage();
            Validate.notNull(criterionPage, "The criterion is not connected to a page!");

            Project projectOfCriterion = criterionPage.getProject();
            Validate.notNull(projectOfCriterion, "The criterion " + criterion.getName() + " has no associated project which is not allowed!");

            if(project == null) {
                project = projectOfCriterion;
            } else if(!projectOfCriterion.equals(project)) {
                throw new IllegalArgumentException("One is trying to mix criterions of different project which is not allowed!");
            }
        }

        this.project = project;
        this.criterionList = criterions;

        computeColumns();
        computeRows();

        run();
    }

    public void setCriterion(Criterion criterion) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(criterion);
        setCriterions(criterions);
    }

    public void setUserChoiceRepository(UserChoiceRepository userChoiceRepository) {
        this.userChoiceRepository = userChoiceRepository;
    }

    protected Project getProject() {
        return this.project;
    }

    public List<Object> getColumns() {
        return columns;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    protected List<Criterion> getCriterions() {
        return criterionList;
    }

    /**
     * Computes the columns of the table.
     */
    private void computeColumns() {
        columns = new ArrayList<Object>();
        columns.add("id");
        columns.add("username");

        for(Criterion criterion : getCriterions()) {
            if(criterion instanceof NominalScaledCriterion) {
                NominalScaledCriterion nsc = (NominalScaledCriterion) criterion;
                int critOrder = nsc.getOrdering();
                if(nsc.isMultipleChoice()) {
                    for(MeasurementValue mv : nsc.getValues()) {
                        int mvOrder = mv.getOrdering();
                        columns.add(mv);
                    }
                } else {
                    columns.add(criterion);
                }
            } else if(criterion instanceof OrdinalScaledCriterion) {
                OrdinalScaledCriterion osc = (OrdinalScaledCriterion) criterion;
                columns.add(criterion);
            }
        }
    }

    private void computeRows() {
        rows = new ArrayList<List<Object>>();

        Set<ProjectMember> projectTeam = getProject().getProjectTeam();
        for (ProjectMember projectMember : projectTeam) {
            List<Object> row = new ArrayList<Object>();
            row.add(projectMember.getUser().getId());
            row.add(projectMember.getUser().getUsername());

            for(Criterion criterion : getCriterions()) {

                UserChoice userChoiceForCriterion = getUserChoiceForCriterion(projectMember.getUser(), criterion);

                if(criterion instanceof NominalScaledCriterion) {
                    computeRowForNominalScaledCriterion(row, criterion, userChoiceForCriterion);
                } else if(criterion instanceof OrdinalScaledCriterion) {
                    computeRowForOrdinalScaledCriterion(row, criterion, userChoiceForCriterion);
                }
            }

            rows.add(row);
        }
    }

    private void computeRowForOrdinalScaledCriterion(List<Object> row, Criterion criterion, UserChoice userChoiceForCriterion) {
        OrdinalScaledCriterion nsc = (OrdinalScaledCriterion) criterion;
        List<? extends MeasurementValue> values = nsc.getValues();
        if(userChoiceForCriterion != null && values.contains(userChoiceForCriterion.getMeasurementValues().get(0))) {
            row.add(userChoiceForCriterion.getMeasurementValues().get(0));
        } else {
            row.add(null);
        }
    }

    private void computeRowForNominalScaledCriterion(List<Object> row, Criterion criterion, UserChoice userChoiceForCriterion) {
        NominalScaledCriterion nsc = (NominalScaledCriterion) criterion;
        List<? extends MeasurementValue> values = nsc.getValues();
        if(nsc.isMultipleChoice()) {
            computeRow(row, userChoiceForCriterion, values);
        } else {
            if(userChoiceForCriterion != null && values.contains(userChoiceForCriterion.getMeasurementValues().get(0))) {
                row.add(userChoiceForCriterion.getMeasurementValues().get(0));
            } else {
                row.add(null);
            }
        }
    }

    /**
     * Checks if a User has selected a MeasurementValue of the given list.
     * If the User has selected a specific MeasurementValue, the selected MeasurementValue
     * will be added to thr row. Otherwise null will be inserted.
     *
     * @param row                       The row to add the value
     * @param userChoice                The acutal UserChoice
     * @param values         Available MeasurementValues
     */
    private void computeRow(List<Object> row, UserChoice userChoice, List<? extends MeasurementValue> values) {
        for(MeasurementValue mv : values) {
            if(userChoice != null && userChoice.getMeasurementValues().contains(mv)) {
                row.add(mv);
            } else {
                row.add(null);
            }
        }
    }

    /**
     * Gets all UserChoices for the given User for the given Criterion out of the database.
     * The UserChoice then contains all selected MeasurementValues for the given Criterion if any has been selected.
     * The list of MeasurementValues may also be null or empty if no MeasurementValues have been selected.
     *
     * @param user              The user to lookup, must not be null!
     * @param criterion         The criterion to lookup, must not be null!
     * @return                  One UserChoice object if found or null
     */
    UserChoice getUserChoiceForCriterion(User user, Criterion criterion) {
        Validate.notNull(user, "The user may not be null!");
        Validate.notNull(criterion, "The criterion may not be null!");

        return userChoiceRepository.findByUserAndCriterion(user.getId(), criterion.getId());
    }

    protected Object getColumnForValueInRow(List<Object> row, Object field) {
        for(Object fieldInRow : row) {
            if(fieldInRow instanceof MeasurementValue) {
                MeasurementValue mv = (MeasurementValue) fieldInRow;
                if(mv.equals(field)) {
                    int index = row.indexOf(mv);
                    if(index != -1) {
                        return getColumns().get(index);
                    }
                }
            }
        }

        return null;
    }

    protected abstract void run();
    public abstract T getResult();
}
