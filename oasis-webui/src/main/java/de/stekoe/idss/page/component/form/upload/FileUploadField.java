package de.stekoe.idss.page.component.form.upload;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.Panel;

public class FileUploadField extends Panel {
    private final org.apache.wicket.markup.html.form.upload.FileUploadField fileUpload;

    public FileUploadField(String id) {
        super(id);

        fileUpload = new org.apache.wicket.markup.html.form.upload.FileUploadField("fileUpload");
        add(fileUpload);
    }

    public FileUpload getFileUpload() {
        return fileUpload.getFileUpload();
    }
}
