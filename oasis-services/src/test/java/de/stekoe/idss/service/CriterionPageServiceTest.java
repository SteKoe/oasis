package de.stekoe.idss.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.PageElement;

public class CriterionPageServiceTest extends AbstractBaseTest {

    @Inject
    CriterionPageService criterionPageService;

    @Inject
    CriterionService criterionService;

    @Inject
    ProjectService projectService;

    @Test
    public void savePageWithElements() throws Exception {
        CriterionPage page = new CriterionPage();

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("NSC");

        nsc.setCriterionPage(page);
//        page.getPageElements().add(nsc);

        criterionPageService.save(page);

        final String id = page.getId();
        page = criterionPageService.findOne(id);
        Assert.assertNotNull(page);

        assertThat(page.getPageElements().size(), is(equalTo(1)));
        assertThat(page.getPageElements().get(0).getCriterionPage(), is(equalTo(page)));
    }

    @Test
    public void deletePage() throws Exception {
        final CriterionPage page = new CriterionPage();

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("NSC 1");
        nsc.setCriterionPage(page);

        nsc = new NominalScaledCriterion();
        nsc.setName("NSC 2");
        nsc.setCriterionPage(page);

        criterionPageService.save(page);

        final CriterionPage criterionPage = criterionPageService.findOne(page.getId());
        assertThat(criterionPage.getPageElements().size(), is(equalTo(2)));

        criterionPageService.delete(criterionPage.getId());

        for (PageElement pe : criterionPage.getPageElements()) {
            Criterion findOne = criterionService.findOne(pe.getId());
            assertThat(findOne, is(nullValue()));
        }
    }
}
