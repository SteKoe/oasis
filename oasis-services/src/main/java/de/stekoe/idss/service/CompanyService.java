package de.stekoe.idss.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.repository.CompanyRepository;

@Service
@Transactional(readOnly = true)
public class CompanyService {

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private AuthService authService;

    public Company findOne(String id) {
        return companyRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(Company company) {
        companyRepository.save(company);
    }

    public List<Company> findByUser(String userId) {
        return companyRepository.findByUser(userId);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        companyRepository.delete(id);
    }

    public boolean isAuthorized(final String userId, final String companyId, final PermissionType permissionType) {
        Company company = companyRepository.findOne(companyId);

        // Has the user any permissions like administrative permissions?
        if (authService.isAuthorized(userId, company, permissionType)) {
            return true;
        }

        List<Employee> employees = company.getEmployees();
        Employee emp = (Employee) CollectionUtils.find(employees, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                Employee emp = (Employee) object;
                if(emp.getUser() == null) {
                    return false;
                } else {
                    return emp.getUser().getId().equals(userId);
                }
            }
        });

        // User is no employee of the current company
        if(emp == null) {
            return false;
        } else {
            // When "READ" permission is required and user is employee, grant access
            if(PermissionType.READ.equals(permissionType)) {
                return true;
            }
        }

        CompanyRole role = emp.getRole();
        if(role == null) {
            return false;
        }
        for(Permission permission : role.getPermissions()) {
            if(permission.getPermissionObject().equals(PermissionObject.valueOf(Company.class))) {
                if(permission.getPermissionType().equals(PermissionType.ALL) || permission.getPermissionType().equals(permissionType)) {
                    return true;
                }
            }
        }


        return false;
    }

    public List<Company> findAll() {
        return (List<Company>) companyRepository.findAll();
    }
}
