package de.stekoe.oasis;

import de.stekoe.oasis.conf.AppConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@WebAppConfiguration
@ContextHierarchy(value = {
        @ContextConfiguration(value = {"classpath*:services/TestBeanLocations.xml"}),
        @ContextConfiguration(classes = AppConfiguration.class)
})

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public abstract class AbstractWebAppTest {
}
