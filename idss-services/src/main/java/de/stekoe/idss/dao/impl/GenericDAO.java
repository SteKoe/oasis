/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IGenericDAO;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAO<T> implements IGenericDAO<T> {

    private static final Logger LOG = Logger.getLogger(GenericDAO.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(T entity) {
        try {
            getSession().merge(entity);
            getSession().flush();
            getSession().clear();
        } catch (ObjectNotFoundException e) {
            LOG.warn("Saving entity using merge() failed, trying to use saveOrUpdate() instead " + entity, e);
            getSession().saveOrUpdate(entity);
        } catch (HibernateException e) {
            LOG.error("Error during saving entity " + entity, e);
        }
    }

    @Override
    public void delete(Serializable id) {
        delete(findById(id));
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
        getSession().flush();
        getSession().clear();
    }

    @Override
    public T findById(Serializable id) {
        return (T) getSession().get(getPersistedClass(), id);
    }

    @Override
    public List<T> findAll() {
        return getSession().createCriteria(getPersistedClass()).list();
    }

    protected abstract Class<? extends Serializable> getPersistedClass();
}
