package de.stekoe.idss.component.form.upload;

import de.stekoe.idss.model.Document;
import de.stekoe.idss.service.DocumentService;
import de.stekoe.idss.session.WebSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import java.io.File;
import java.io.IOException;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class DocumentUploadForm extends Panel {

    private static final Logger LOG = Logger.getLogger(DocumentUploadForm.class);

    @SpringBean
    DocumentService documentService;

    private FileUploadField fileUpload;

    public DocumentUploadForm(String id) {
        super(id);

        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {

                final FileUpload uploadedFile = fileUpload.getFileUpload();
                if (uploadedFile != null) {

                    Document document = new Document();
                    document.setName(uploadedFile.getClientFileName());
                    document.setContentType(StringUtils.substringAfterLast(uploadedFile.getClientFileName(), "."));
                    document.setSize(uploadedFile.getSize());
                    document.setUser(WebSession.get().getUser());

                    try {
                        final String absolutePath = documentService.getAbsolutePath(document.getId());

                        File newFile = new File(absolutePath);
                        createFolders(newFile);
                        createFile(newFile);
                        uploadedFile.writeTo(newFile);

                        documentService.save(document);

                        onAfterSubmit(document);
                    } catch (Exception e) {
                        LOG.error("Error uploading file", e);
                    }
                }
            }

            private boolean createFile(File newFile) throws IOException {
                return newFile.createNewFile();
            }

            private void createFolders(File newFile) {
                if(!newFile.getParentFile().exists())
                    newFile.getParentFile().mkdirs();
            }
        };

        form.setMultiPart(true);
        form.setMaxSize(Bytes.megabytes(50));
        form.add(fileUpload = new FileUploadField("fileUpload"));

        add(form);
    }

    public abstract void onAfterSubmit(Document document);
}
