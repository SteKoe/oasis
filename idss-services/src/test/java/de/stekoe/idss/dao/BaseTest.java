package de.stekoe.idss.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:services/BeanLocations.xml","classpath:services/TestBeanLocations.xml"})
@TransactionConfiguration
@Transactional
public abstract class BaseTest {

    @Autowired
    SessionFactory sessionFactory;

    protected void flush() {
        this.sessionFactory.getCurrentSession().flush();
    }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
