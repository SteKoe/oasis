package de.stekoe.idss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.UserChoice;

public interface UserChoiceRepository extends CrudRepository<UserChoice, String> {
    @Query("SELECT DISTINCT uc FROM UserChoice uc WHERE uc.criterion.id = ?2 AND uc.user.id = ?1")
    UserChoice findByUserAndCriterion(String userId, String criterionId);

    @Query("SELECT uc FROM UserChoice uc WHERE uc.criterion.id = ?2 AND uc.project.id = ?1")
    UserChoice findByProjectAndCriterion(String projectId, String criterionId);

    @Query("SELECT uc FROM UserChoice uc JOIN uc.measurementValues mv WITH mv.id = ?2")
    List<UserChoice> findByMeasurementValue(String id);

    @Query("SELECT DISTINCT uc FROM UserChoice uc WHERE uc.criterion.id = ?1")
    List<UserChoice> findByCriterionId(String criterionId);
}
