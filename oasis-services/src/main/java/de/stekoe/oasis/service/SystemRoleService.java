package de.stekoe.oasis.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.model.SystemRole;
import de.stekoe.oasis.repository.SystemRoleRepository;

@Service
@Transactional(readOnly = true)
public class SystemRoleService {

    @Inject
    SystemRoleRepository systemRoleRepository;

    public List<SystemRole> findAll() {
        return (List<SystemRole>) systemRoleRepository.findAll();
    }

    public SystemRole getAdminRole() {
        return systemRoleRepository.getRoleByName(SystemRole.ADMIN);
    }

    public SystemRole getUserRole() {
        return systemRoleRepository.getRoleByName(SystemRole.USER);
    }

    public SystemRole findById(String id) {
        return systemRoleRepository.findOne(id);
    }

    @Transactional
    public void save(SystemRole systemRole) {
        systemRoleRepository.save(systemRole);
    }

    @Transactional
    public void delete(SystemRole entity) {
        systemRoleRepository.delete(entity);
    }

    public SystemRole getRoleByName(String rolename) {
        return systemRoleRepository.getRoleByName(rolename);
    }

    public long count() {
        return systemRoleRepository.count();
    }
}