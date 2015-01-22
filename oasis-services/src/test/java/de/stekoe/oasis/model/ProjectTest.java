package de.stekoe.oasis.model;

import static org.junit.Assert.assertFalse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


public class ProjectTest {
    @Test
    public void idIsAssignedProperly() throws Exception {
        Project project = new Project();
        assertFalse(StringUtils.isBlank(project.getId()));
    }
}
