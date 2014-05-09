package de.stekoe.idss.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;

public class UserChoiceRepositoryImpl implements UserChoiceRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<User, List<UserChoice>> findUserChoicesGroupedByUser(List<Criterion> criterionList) {
        Validate.notNull(entityManager);

        Map<User, List<UserChoice>> choicesPerUser = new HashMap<User, List<UserChoice>>();
        Query qry = entityManager.createQuery("SELECT uc FROM UserChoice uc LEFT JOIN uc.user u WHERE uc.project.id = :projectId");
        qry.setParameter("projectId", null);
        List<UserChoice> userChoices = qry.getResultList();
        for(UserChoice userChoice : userChoices) {
            User user = userChoice.getUser();
            if(choicesPerUser.containsKey(user)) {
                choicesPerUser.get(user).add(userChoice);
            } else {
                ArrayList<UserChoice> choices = new ArrayList<UserChoice>();
                choices.add(userChoice);
                choicesPerUser.put(user, choices);
            }
        }

        return choicesPerUser;
    }

}
