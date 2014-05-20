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

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.repository.CriterionPageRepository;

@Service
@Transactional(readOnly = true)
public class CriterionPageService {

    @Inject
    private CriterionPageRepository criterionPageRepository;

    public CriterionPage findOne(String id) {
        return criterionPageRepository.findOne(id);
    }

    @Transactional
    public void save(CriterionPage entity) {
        criterionPageRepository.save(entity);
    }

    @Transactional
    public void delete(String criterionPageId) {
        final Project project = findOne(criterionPageId).getProject();
        criterionPageRepository.delete(criterionPageId);
    }

    public List<CriterionPage> getCriterionPagesForProject(String projectId) {
        return criterionPageRepository.findAllForProject(projectId);
    }

    public List<CriterionPage> findAllForProject(String id) {
        return criterionPageRepository.findAllForProject(id);
    }

    public List<CriterionPage> findAll() {
        return (List<CriterionPage>) criterionPageRepository.findAll();
    }
}
