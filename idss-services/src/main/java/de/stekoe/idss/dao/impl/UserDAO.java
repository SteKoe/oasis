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

import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class UserDAO extends GenericDAO<User> implements IUserDAO {
    @Override
    public User findByUsername(String username) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findByActivationCode(java.lang.String code) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("activationKey", code));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findByEmail(java.lang.String email) {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        return (User) criteria.uniqueResult();
    }

    @Override
    public List<User> findAllByUsername(String username) {
        final Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.ilike("username", "%" + username + "%"));
        return criteria.list();
    }

    @Override
    protected Class<User> getPersistedClass() {
        return User.class;
    }
}
