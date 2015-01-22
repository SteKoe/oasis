package de.stekoe.oasis.web.reports;

import de.stekoe.oasis.model.*;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class MultiCriterionReport<T> implements Serializable {

    private List<Object> columns = new ArrayList<>();
    private List<List<Object>> rows = new ArrayList<>();
    private Project project;
    private List<Criterion> criterions;
    private List<UserChoices> userChoiceses;
    private boolean initialized = false;

    public MultiCriterionReport(List<Criterion> criterions, List<UserChoices> userChoiceses) {
        this.criterions = criterions;
        this.userChoiceses = userChoiceses;
        setAndCheckProject();

        Validate.isTrue(getProject() != null, "Project is null!");

        computeColumns();
        computeRows();
    }

    protected void setAndCheckProject() {
        for(Criterion criterion : getCriterions()) {
            if(criterion != null) {
                CriterionPage criterionPage =  criterion.getCriterionPage();
                if(criterionPage == null) {
                    criterionPage =  criterion.getCriterionGroup().getCriterionPage();
                }
                Validate.notNull(criterionPage, "The criterion is not connected to a page!");

                Project projectOfCriterion = criterionPage.getProject();
                Validate.notNull(projectOfCriterion, "The criterion " +  criterion.getName() + " has no associated project which is not allowed!");

                // Check if one does not mix criterions
                if(this.project != null) {
                    Validate.isTrue(this.project.equals(projectOfCriterion), "");
                }

                this.project = projectOfCriterion;
            }
        }
    }

    private void computeColumns() {
        columns.add("userid");
        columns.add("username");
        columns.add("projectMember");
        columns.addAll(getCriterions());
    }

    private void computeRows() {
        Set<ProjectMember> projectTeam = getProject().getProjectTeam();
        for (ProjectMember projectMember : projectTeam) {
            List<Object> row = new ArrayList<>();
            row.add(projectMember.getUser().getId());
            row.add(projectMember.getUser().getUsername());
            row.add(projectMember);

            for(Criterion criterion : getCriterions()) {
                // Retrieve all choices made by user for given Criterion "criterion"
                UserChoice userChoiceForCriterion = getUserChoiceForCriterion(projectMember.getUser(), criterion);

                if(userChoiceForCriterion == null) {
                    row.add(Collections.EMPTY_LIST);
                } else {
                    if(criterion instanceof NominalScaledCriterion) {
                        computeRowForNominalScaledCriterion(row, (NominalScaledCriterion) criterion, userChoiceForCriterion);
                    } else if(criterion instanceof OrdinalScaledCriterion) {
                        computeRowForOrdinalScaledCriterion(row, (OrdinalScaledCriterion) criterion, userChoiceForCriterion);
                    }
                }
            }
            rows.add(row);
        }
    }

    private void computeRowForNominalScaledCriterion(List<Object> row, NominalScaledCriterion criterion, UserChoice userChoiceForCriterion) {
        List<NominalValue> values = criterion.getValues()
                .stream()
                .filter(val -> userChoiceForCriterion.getChoices().contains(val.getId()))
                .collect(Collectors.toList());

        if(userChoiceForCriterion.getChoices().contains("::nv::")) {
            NominalValue nv = new NominalValue();
            nv.setValue("measurement.no.value");
            values.add(nv);
        }
        row.add(values);
    }

    private void computeRowForOrdinalScaledCriterion(List<Object> row, OrdinalScaledCriterion criterion, UserChoice userChoiceForCriterion) {
        List<OrdinalValue> values = criterion.getValues()
                .stream()
                .filter(val -> userChoiceForCriterion.getChoices().contains(val.getId()))
                .collect(Collectors.toList());

        if(userChoiceForCriterion.getChoices().contains("::nv::")) {
            OrdinalValue ov = new OrdinalValue();
            ov.setValue("measurement.no.value");
            values.add(ov);
        }
        row.add(values);
    }

    UserChoice getUserChoiceForCriterion(User user, Criterion criterion) {
        Validate.notNull(user, "The user may not be null!");
        Validate.notNull(criterion, "The criterion may not be null!");

        Optional<UserChoices> userChoices = userChoiceses
                .stream()
                .filter(uc -> uc.getUser().getId().equals(user.getId()))
                .filter(uc -> uc.getProject().equals(getProject()))
                .findAny();

        if(userChoices.isPresent()) {
            return userChoices.get().getUserChoices().get(criterion.getId());
        }

        return null;
    }

    protected List<Criterion> getCriterions() {
        return this.criterions;
    }

    public Project getProject() {
        return project;
    }

    public List<Object> getColumns() {
        return this.columns;
    }

    public List<List<Object>> getRows() {
        return this.rows;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public abstract void run();
    public abstract T getResult();
}
