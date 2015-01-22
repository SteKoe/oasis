package de.stekoe.oasis.repository;

import java.util.List;

import de.stekoe.oasis.model.Project;
import de.stekoe.oasis.model.User;
import de.stekoe.oasis.model.UserChoices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserChoicesRepository extends CrudRepository<UserChoices, String> {
    @Query("SELECT uc FROM UserChoices uc WHERE uc.user = ?1 AND uc.project = ?2")
    UserChoices findByUserAndProject(User user, Project project);

    @Query("SELECT uc FROM UserChoices uc WHERE uc.project.id = ?1")
    List<UserChoices> findByProject(String projectId);
}
