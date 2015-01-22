package de.stekoe.oasis.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.model.ProjectMember;
import de.stekoe.oasis.repository.ProjectMemberRepository;

@Service
@Transactional(readOnly = true)
public class ProjectMemberService  {

    @Inject
    ProjectMemberRepository projectMemberRepository;

    public ProjectMember findOne(String id) {
        return projectMemberRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(ProjectMember projectMember) {
        projectMemberRepository.save(projectMember);
    }

    @Transactional(readOnly = false)
    public void delete(ProjectMember entity) {
        projectMemberRepository.delete(entity);
    }
}
