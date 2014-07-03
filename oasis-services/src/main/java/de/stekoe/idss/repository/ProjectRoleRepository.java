package de.stekoe.idss.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.ProjectRole;

public interface ProjectRoleRepository extends CrudRepository<ProjectRole, String> {
    @Query("FROM ProjectRole pr WHERE pr.name = ?1")
    ProjectRole findByName(String rolename);
}
