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
