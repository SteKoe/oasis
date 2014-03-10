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

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.project.ProjectId;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class CriterionPageDAO extends GenericDAO<CriterionPage> implements ICriterionPageDAO {

    @Override
    public List<CriterionPage> findAllForProject(ProjectId projectId) {
        final Criteria criteria = getSession().createCriteria(getPersistedClass());
        criteria.add(Restrictions.eq("project.id", projectId));
        criteria.addOrder(Order.asc("ordering"));
        return criteria.list();
    }

    @Override
    public int getNextPageNumForProject(ProjectId projectId) {
        final Criteria criteria = getSession().createCriteria(getPersistedClass());
        criteria.add(Restrictions.eq("project.id", projectId));
        criteria.setProjection(Projections.max("ordering"));

        final Object o = criteria.uniqueResult();
        if (o == null) {
            return 1;
        }

        return (int) o + 1;
    }

    @Override
    public CriterionPage findByOrdering(int ordering, ProjectId projectId) {
        final Criteria criteria = getSession().createCriteria(getPersistedClass());
        criteria.add(Restrictions.eq("project.id", projectId));
        criteria.add(Restrictions.eq("ordering", ordering));
        return (CriterionPage) criteria.uniqueResult();
    }

    @Override
    public CriterionPage findPageOfCriterionElement(SingleScaledCriterion aCriterion) {
        final Criteria criteria = getSession().createCriteria(getPersistedClass());
        criteria.add(Restrictions.eq("pageElements.id", aCriterion.getId()));
        return (CriterionPage) criteria.uniqueResult();
    }

    @Override
    protected Class<CriterionPage> getPersistedClass() {
        return CriterionPage.class;
    }
}
