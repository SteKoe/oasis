package de.stekoe.idss.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.ProjectMember;

public interface ProjectMemberRepository extends CrudRepository<ProjectMember, String> {
}
