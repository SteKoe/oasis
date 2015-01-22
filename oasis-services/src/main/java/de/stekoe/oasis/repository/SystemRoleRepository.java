package de.stekoe.oasis.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.SystemRole;

public interface SystemRoleRepository extends CrudRepository<SystemRole, String> {
    @Query("FROM SystemRole sr WHERE sr.name = ?1")
    SystemRole getRoleByName(String rolename);
}
