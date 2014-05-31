package de.stekoe.idss.page.component;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PaginationInfoLabelTest {

    /**
     * 1 2 3 4 | 5 6 7 8 | 9 10 x x
     *
     * @throws Exception
     */
    @Test
    public void testName() throws Exception {
        int itemsPerPage = 4;
        int pages = 3;
        int totalElements = 10;
        int currentPage = 1;

        PaginationInfoLabel label = new PaginationInfoLabel(itemsPerPage, pages, totalElements, currentPage);

        assertThat(label.getItemCountOnCurrentPage(), is(equalTo((long)4)));
        assertThat(label.getStart(), is(equalTo((long)1)));
        assertThat(label.getEnd(), is(equalTo((long)4)));

        label.setCurrentPage(2);
        assertThat(label.getItemCountOnCurrentPage(), is(equalTo((long)4)));
        assertThat(label.getStart(), is(equalTo((long)5)));
        assertThat(label.getEnd(), is(equalTo((long)8)));

        label.setCurrentPage(3);
        assertThat(label.getItemCountOnCurrentPage(), is(equalTo((long)2)));
        assertThat(label.getStart(), is(equalTo((long)9)));
        assertThat(label.getEnd(), is(equalTo((long)10)));
    }
}
