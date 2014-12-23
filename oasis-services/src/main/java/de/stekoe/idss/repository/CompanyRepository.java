package de.stekoe.idss.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.Company;

public interface CompanyRepository extends CrudRepository<Company, String> {
    /**
     * Finds any Company where the given user is employed.
     *
     * @param userId The userid to check
     * @return a list of Companys a user is employed.
     */
    @Query("SELECT c FROM Company c JOIN c.employees emp JOIN emp.user u WHERE u.id = ?1")
    Company findByUser(String userId);

    List<Company> findByNameLike(String name, Pageable page);

    Company findByRegistrationToken(String registryToken);

    @Query("SELECT c FROM Company c JOIN c.employees emp JOIN emp.user u WHERE u.username = ?1 OR u.email = ?1")
    Company findByUsername(String username);
}
