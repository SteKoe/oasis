package de.stekoe.oasis.service;

import de.stekoe.oasis.model.Employee;
import de.stekoe.oasis.model.User;
import de.stekoe.oasis.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    public List<Employee> findByUser(User user) {
        return employeeRepository.findByUser(user);
    }
}
