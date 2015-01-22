package de.stekoe.oasis.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, String> {
}
