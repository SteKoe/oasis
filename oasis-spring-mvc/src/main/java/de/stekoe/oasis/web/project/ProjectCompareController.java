package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ProjectService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ProjectCompareController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionService criterionService;

    private Map<Criterion, List<MeasurementValueCompareResult>> matchingCriterions = new LinkedHashMap<>();
    private Map<Criterion, List<MeasurementValueCompareResult>> nonMatchingCriterions = new LinkedHashMap<>();

    @RequestMapping(value = "/project/compare")
    public ModelAndView compare() {

        Project left = projectService.findOne("8bef880f-ed76-4bfd-9853-ade6af487028");
        Project right = projectService.findOne("537a4187-a27a-403b-a7ab-9654af6eef17");

        List<Criterion> leftCriterions = criterionService.findAllForReport(left.getId());
        List<Criterion> rightCriterions = criterionService.findAllForReport(right.getId());

        computeEqualCriterions(leftCriterions, rightCriterions);

        ModelAndView model = new ModelAndView("/project/compare");
        model.addObject("matchingCriterions", matchingCriterions);
        model.addObject("nonMatchingCriterions", nonMatchingCriterions);
        return model;
    }

    private void computeEqualCriterions(List<Criterion> leftCriterions, List<Criterion> rightCriterions) {
        leftCriterions.forEach(leftCriterion -> {
            rightCriterions.forEach(rightCriterion -> {
                if(leftCriterion.getName().equalsIgnoreCase(rightCriterion.getName())) {
                    List<MeasurementValueCompareResult> mvcrs = computeEqualMeasurementValues(leftCriterion, rightCriterion);
                    matchingCriterions.put(leftCriterion, mvcrs);
                } else {
                    nonMatchingCriterions.put(leftCriterion, null);
                }
            });
        });
    }

    private List<MeasurementValueCompareResult> computeEqualMeasurementValues(Criterion leftCriterion, Criterion rightCriterion) {
        List<MeasurementValueCompareResult> mvcrs = new LinkedList<MeasurementValueCompareResult>();
        if(leftCriterion instanceof SingleScaledCriterion && rightCriterion instanceof SingleScaledCriterion) {
            SingleScaledCriterion leftSsc = (SingleScaledCriterion) leftCriterion;
            SingleScaledCriterion rightSsc = (SingleScaledCriterion) rightCriterion;

            List<MeasurementValue> leftValues = leftSsc.getValues();
            List<MeasurementValue> rightValues = rightSsc.getValues();

            List<MeasurementValue> unmatchedRightValues = new LinkedList<>();
            rightValues.forEach(val -> {
                Optional<MeasurementValue> any = leftValues.stream().filter(leftVal -> leftVal.getValue().equalsIgnoreCase(val.getValue())).findAny();
                if(!any.isPresent()) {
                    unmatchedRightValues.add(val);
                }
            });

            leftValues.forEach(leftValue -> {
                MeasurementValueCompareResult mvcr = new MeasurementValueCompareResult();
                mvcr.setLeft((MeasurementValue) leftValue);

                Optional<MeasurementValue> any = rightValues.stream().filter(val -> val.getValue().equalsIgnoreCase(leftValue.getValue())).findAny();

                if(any.isPresent()) {
                    mvcr.setRight(any.get());
                } else {
                    mvcr.setRight(null);
                }
                mvcrs.add(mvcr);
            });

            unmatchedRightValues.forEach(val -> {
                MeasurementValueCompareResult mvcr = new MeasurementValueCompareResult();
                mvcr.setLeft(null);
                mvcr.setRight(val);
                mvcrs.add(mvcr);
            });
        }

        return mvcrs;
    }

    public class MeasurementValueCompareResult {
        private MeasurementValue left;
        private MeasurementValue right;

        public MeasurementValue getLeft() {
            return left;
        }

        public void setLeft(MeasurementValue left) {
            this.left = left;
        }

        public MeasurementValue getRight() {
            return right;
        }

        public void setRight(MeasurementValue right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append(left)
                    .append(right)
                    .toString();
        }
    }
}
