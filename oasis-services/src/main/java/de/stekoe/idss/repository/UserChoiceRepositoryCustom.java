package de.stekoe.idss.repository;

import java.util.List;
import java.util.Map;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;

public interface UserChoiceRepositoryCustom {
    Map<User, List<UserChoice>> findUserChoicesGroupedByUser(List<Criterion> criterionList);
}
