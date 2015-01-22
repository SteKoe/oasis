package de.stekoe.oasis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.model.Permission;
import de.stekoe.oasis.repository.PermissionRepository;

@Service
@Transactional(readOnly = true)
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Transactional
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }

    @Transactional
    public void delete(Permission permission) {
        permissionRepository.delete(permission);
    }
}
