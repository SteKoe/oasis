package de.stekoe.idss.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, String> {
}
