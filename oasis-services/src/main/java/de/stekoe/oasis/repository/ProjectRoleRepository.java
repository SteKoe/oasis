package de.stekoe.oasis.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.ProjectRole;

public interface ProjectRoleRepository extends CrudRepository<ProjectRole, String> {
    @Query("FROM ProjectRole pr WHERE pr.name = ?1")
    ProjectRole findByName(String rolename);
}
