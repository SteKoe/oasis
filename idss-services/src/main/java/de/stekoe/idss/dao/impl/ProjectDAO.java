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

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.UserId;
import de.stekoe.idss.model.project.Project;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class ProjectDAO extends GenericDAO<Project> implements IProjectDAO {

    @Override
    public List<Project> findByProjectName(java.lang.String projectName) {
        Criteria criteria = getSession().createCriteria(Project.class);
        criteria.add(Restrictions.eq("name", projectName));
        return criteria.list();
    }

    @Override
    public List<Project> findAllForUser(UserId user) {
        final Criteria criteria = getAllProjectsForUser(user);
        return criteria.list();
    }

    private Criteria getAllProjectsForUser(UserId userId) {
        DetachedCriteria idsOnlyCriteria = DetachedCriteria.forClass(Project.class);
        idsOnlyCriteria.createCriteria("projectTeam").add(Restrictions.eq("user.id", userId));
        idsOnlyCriteria.setProjection(Projections.distinct(Projections.id()));

        final Criteria criteria = getSession().createCriteria(Project.class);
        criteria.add(Subqueries.propertyIn("id", idsOnlyCriteria));
        return criteria;
    }

    @Override
    public List<Project> findAllForUserPaginated(UserId userId, int perPage, int curPage) {
        final Criteria criteria = getAllProjectsForUser(userId);
        criteria.setFirstResult(perPage * curPage);
        criteria.setMaxResults(perPage);
        return criteria.list();
    }

    @Override
    protected Class<Project> getPersistedClass() {
        return Project.class;
    }
}
