package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.SystemroleDAO;
import de.stekoe.idss.model.Systemrole;

public class SystemroleDAOImpl extends GenericDAOImpl implements SystemroleDAO {

    @Override
    public void saveOrUpdate(Systemrole systemrole) {
        getCurrentSession().saveOrUpdate(systemrole);
    }

}
