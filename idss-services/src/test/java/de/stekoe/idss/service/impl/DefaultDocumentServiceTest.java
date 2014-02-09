package de.stekoe.idss.service.impl;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.service.DocumentService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultDocumentServiceTest extends AbstractBaseTest {
    private static final Logger LOG = Logger.getLogger(DefaultDocumentServiceTest.class);

    @Inject
    DocumentService documentService;

    private Set<String> paths;

    @Before
    public void setUp() {
        paths = new HashSet<String>();
    }

    @Test
    public void testGetAbsolutePath() throws Exception {
        for(int i = 0; i <= 1000; i++) {
            final String s = UUID.randomUUID().toString();
            final String path = documentService.getAbsolutePath(s);
            if(!paths.add(path)) {
                    throw new IllegalArgumentException();
            }
        }
    }
}
