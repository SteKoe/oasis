package de.stekoe.idss.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.repository.CompanyRoleRepository;

@Service
@Transactional(readOnly = true)
public class CompanyRoleService {

    @Inject
    private CompanyRoleRepository companyRoleRepository;

    public CompanyRole findOne(String id) {
        return companyRoleRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(CompanyRole companyRole) {
        companyRoleRepository.save(companyRole);
    }
}
