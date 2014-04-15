package de.stekoe.idss.wicket.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.service.CompanyService;


public class CompanyModelTest extends AbstractWicketApplicationTester {

    @Inject
    CompanyService companyService;

    @Inject
    CompanyModel companyModel;

    @Test
    public void getObject() throws Exception {
        Company c = new Company();
        c.setName("ACME");
        companyService.save(c);

        companyModel.setId(c.getId());

        Company companyFromDB = companyModel.getObject();
        assertNotNull(companyFromDB);
        assertThat(companyFromDB.getId(), IsEqual.equalTo(c.getId()));
    }
}
