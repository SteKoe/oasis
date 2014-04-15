/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.repository.CompanyRepository;

@Service
@Transactional(readOnly = true)
public class CompanyService {

    @Inject
    private CompanyRepository companyRepository;

    public Company findOne(String id) {
        return companyRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(Company company) {
        companyRepository.save(company);
    }
}
