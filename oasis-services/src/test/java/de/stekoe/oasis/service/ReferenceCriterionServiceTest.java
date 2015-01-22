package de.stekoe.oasis.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import de.stekoe.oasis.AbstractBaseTest;
import de.stekoe.oasis.model.Criterion;
import de.stekoe.oasis.model.NominalScaledCriterion;


public class ReferenceCriterionServiceTest extends AbstractBaseTest {

    @Inject
    ReferenceCriterionService referenceCriterionService;

    private final List<Criterion> criterions = new ArrayList<Criterion>();

    @Before
    public void setUp() {
        // Not a reference type!!!
        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("A");
        criterions.add(nsc);

        // Is a reference type
        nsc = new NominalScaledCriterion();
        nsc.setName("B");
        nsc.setReferenceType(true);
        criterions.add(nsc);

        // Is a reference type
        nsc = new NominalScaledCriterion();
        nsc.setName("C");
        nsc.setReferenceType(true);
        criterions.add(nsc);

        referenceCriterionService.save(criterions);
    }

    @Test
    public void delete() throws Exception {
        assertThat(referenceCriterionService.count(), is(equalTo(((long)2))));
        referenceCriterionService.delete(criterions.get(2).getId());
        assertThat(referenceCriterionService.count(), is(equalTo(((long)1))));
    }

    @Test
    public void count() throws Exception {
        assertThat(referenceCriterionService.count(), is(equalTo(((long)2))));
    }

    @Test
    public void findAll() throws Exception {
        List<Criterion> findAll = (List<Criterion>) referenceCriterionService.findAll();
        assertThat(findAll.size(), is(equalTo(2)));
        assertThat(findAll.contains(criterions.get(0)), is(false));
        assertThat(findAll.contains(criterions.get(1)), is(true));
        assertThat(findAll.contains(criterions.get(2)), is(true));
    }

    @Test
    public void findAllPageable() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<Criterion> findAll = referenceCriterionService.findAll(pageRequest);
        assertThat(findAll.getContent().size(), is(equalTo(1)));

        pageRequest = new PageRequest(1, 1);
        findAll = referenceCriterionService.findAll(pageRequest);
        assertThat(findAll.getContent().size(), is(equalTo(1)));

        pageRequest = new PageRequest(2, 1);
        findAll = referenceCriterionService.findAll(pageRequest);
        assertThat(findAll.getContent().size(), is(equalTo(0)));
    }
}
