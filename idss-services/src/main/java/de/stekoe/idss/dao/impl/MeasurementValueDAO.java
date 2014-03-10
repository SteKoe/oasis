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

import de.stekoe.idss.dao.IMeasurementValueDAO;
import de.stekoe.idss.model.criterion.scale.ScaleId;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class MeasurementValueDAO extends GenericDAO<MeasurementValue> implements IMeasurementValueDAO {
    @Override
    protected Class<MeasurementValue> getPersistedClass() {
        return MeasurementValue.class;
    }

    @Override
    public MeasurementValue findByOrdering(int ordering, ScaleId scaleId) {
        final Criteria criteria = getSession().createCriteria(getPersistedClass());
        criteria.add(Restrictions.eq("scale.id", scaleId));
        criteria.add(Restrictions.eq("ordering", ordering));
        return (MeasurementValue) criteria.uniqueResult();
    }
}
