package de.stekoe.oasis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.model.ProjectRole;
import de.stekoe.oasis.repository.ProjectRoleRepository;

@Service
@Transactional(readOnly = true)
public class ProjectRoleService  {

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    public ProjectRole findOne(String id) {
        return projectRoleRepository.findOne(id);
    }

    @Transactional
    public void save(ProjectRole role) {
        projectRoleRepository.save(role);
    }

    @Transactional
    public void delete(ProjectRole entity) {
        projectRoleRepository.delete(entity);
    }
}
