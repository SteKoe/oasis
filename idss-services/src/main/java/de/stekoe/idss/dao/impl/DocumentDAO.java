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

package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IDocumentDAO;
import de.stekoe.idss.model.Document;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class DocumentDAO extends GenericDAO<Document> implements IDocumentDAO {
    @Override
    protected Class<Document> getPersistedClass() {
        return Document.class;
    }
}
