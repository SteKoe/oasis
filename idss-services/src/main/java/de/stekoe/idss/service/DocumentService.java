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

package de.stekoe.idss.service;

import java.io.File;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Document;
import de.stekoe.idss.repository.DocumentRepository;

@Service
@Transactional
public class DocumentService {
    @Inject
    private DocumentRepository documentRepository;

    private String path;

    public void save(Document document) {
        documentRepository.save(document);
    }

    public String getAbsolutePath(String id) {
        final String[] idParts = id.split("-");
        return getDocumentPath() + File.separator + idParts[1] + File.separator + idParts[0] + ".data";
    }

    public void setDocumentPath(String path) {
        this.path = path;
    }

    public String getDocumentPath() {
        return path;
    }
}
