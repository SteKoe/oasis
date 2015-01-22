package de.stekoe.oasis.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.ProjectMember;

public interface ProjectMemberRepository extends CrudRepository<ProjectMember, String> {
}
