package de.stekoe.oasis.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserChoiceRepositoryImpl implements UserChoiceRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


}
