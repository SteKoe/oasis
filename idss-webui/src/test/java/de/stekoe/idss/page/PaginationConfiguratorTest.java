package de.stekoe.idss.page;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.page.project.ProjectListPage;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;

import static org.junit.Assert.assertThat;

public class PaginationConfiguratorTest extends AbstractWicketApplicationTester{

    @Inject
    private PaginationConfigurator paginationConfigurator;

    @Test
    @DirtiesContext
    public void getDefaultValueIfClassIsNotInList() throws Exception {
        int entriesPerPage = paginationConfigurator.getValueFor(HomePage.class);
        assertThat(entriesPerPage, IsEqual.equalTo(25));
    }

    @Test
    @DirtiesContext
    public void getValueForDefinedClass() throws Exception {
        final int entriesPerPage = paginationConfigurator.getValueFor(ProjectListPage.class);
        assertThat(entriesPerPage, IsEqual.equalTo(50));
    }
}
