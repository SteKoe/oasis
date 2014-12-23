package de.stekoe.idss.repository;

import de.stekoe.idss.model.Employee;
import de.stekoe.idss.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.user = ?1")
    List<Employee> findByUser(User user);
}
