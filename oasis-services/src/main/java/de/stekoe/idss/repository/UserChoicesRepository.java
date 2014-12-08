package de.stekoe.idss.repository;

import java.util.List;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.UserChoice;

public interface UserChoicesRepository extends CrudRepository<UserChoices, String> {
    @Query("SELECT uc FROM UserChoices uc WHERE uc.user = ?1 AND uc.project = ?2")
    UserChoices findByUserAndProject(User user, Project project);
}
