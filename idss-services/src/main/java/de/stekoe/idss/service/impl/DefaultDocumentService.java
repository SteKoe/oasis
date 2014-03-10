/*
 * Copyright 2014 Stephan Köninger
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

package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IDocumentDAO;
import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.DocumentId;
import de.stekoe.idss.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultDocumentService implements DocumentService {
    @Inject
    private IDocumentDAO documentDAO;
    private String path;

    @Override
    public void save(Document document) {
        documentDAO.save(document);
    }

    @Override
    public String getAbsolutePath(DocumentId id) {
        final String[] idParts = id.getId().split("-");
        return getDocumentPath() + File.separator + idParts[1] + File.separator + idParts[0] + ".data";
    }

    @Override
    public void setDocumentPath(String path) {
        this.path = path;
    }

    @Override
    public String getDocumentPath() {
        return path;
    }
}
