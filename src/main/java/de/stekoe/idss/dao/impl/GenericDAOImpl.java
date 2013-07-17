package de.stekoe.idss.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.dao.GenericDAO;

public abstract class GenericDAOImpl implements GenericDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
}
