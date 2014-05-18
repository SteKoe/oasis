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

package de.stekoe.idss.page.component.form.upload;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.lang.Bytes;

public abstract class FileUploadForm extends Panel {

    private static final Logger LOG = Logger.getLogger(FileUploadForm.class);

    private final FileUploadField fileUpload;

    public FileUploadForm(String id) {
        super(id);

        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                final FileUpload uploadedFile = fileUpload.getFileUpload();
                LOG.info("A file has been uploaded: " + uploadedFile.getClientFileName());
                onAfterSubmit(uploadedFile);
            }
        };

        form.setMultiPart(true);
        form.setMaxSize(Bytes.megabytes(50));

        fileUpload = new FileUploadField("fileUploadField");
        form.add(fileUpload);

        add(form);
    }

    public abstract void onAfterSubmit(FileUpload uploadedFile);
}
