package de.stekoe.idss.page.component.form.upload;

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

                    final String absolutePath = documentService.getAbsolutePath(document.getId());

                    File newFile = new File(absolutePath);
                    if(createFolders(newFile) && createFile(uploadedFile, newFile)) {
                        documentService.save(document);
                        onAfterSubmit(document);
                    } else {
                        WebSession.get().error(getString("message.error.upload"));
                    }
                }
            }

            private boolean createFile(FileUpload aUploadedFile, File aNewFile) {
                try {
                    aNewFile.createNewFile();
                    aUploadedFile.writeTo(aNewFile);
                } catch (IOException e) {
                    LOG.error("Error uploading file!", e);
                    return false;
                }
                return true;
            }

            private boolean createFolders(File newFile) {
                if(newFile != null) {
                    final File parentFile = newFile.getParentFile();
                    if (parentFile != null && parentFile.exists()) {
                        if(parentFile.mkdirs()) {
                            return true;
                        }
                    }
                }

                LOG.error("Could not create required folders for uploading documents: " + newFile.getAbsolutePath());
                return false;
            }
        };

        form.setMultiPart(true);
        form.setMaxSize(Bytes.megabytes(50));

        fileUpload = new FileUploadField("fileUpload");
        form.add(fileUpload);

        add(form);
    }

    public abstract void onAfterSubmit(Document document);
}
