package de.stekoe.idss.page.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.time.Duration;

import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.page.component.form.upload.FileUploadForm;
import de.stekoe.idss.service.DocumentService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

public class ProjectUploadDocument extends ProjectPage {

    @SpringBean
    private ProjectService projectService;
    @SpringBean
    private DocumentService documentService;

    public ProjectUploadDocument(PageParameters pageParameters) {
        super(pageParameters);

        addUploadForm();
        addDocumentList();

        final WebMarkupContainer emptyList = new WebMarkupContainer("files.list.empty");
        emptyList.setVisible(getDocumentsOfCurrentProject().isEmpty());
        add(emptyList);
    }

    private void addDocumentList() {
        final List<Document> documentsList = new ArrayList<Document>(getDocumentsOfCurrentProject());
        Collections.sort(documentsList, new Comparator<Document>() {
            @Override
            public int compare(Document d1, Document d2) {
                return d1.getCreated().compareTo(d2.getCreated());
            }
        });

        final ListView<Document> filesList = new ListView<Document>("files.list", documentsList) {
            @Override
            protected void populateItem(ListItem<Document> item) {
                final Document document = item.getModelObject();

                item.add(new Label("file.name", document.getName()));
                item.add(new Label("file.size", Bytes.bytes(document.getSize()).toString(WebSession.get().getLocale()) + "B"));
                item.add(new Label("file.type", document.getContentType()));

                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, WebSession.get().getLocale());
                String formattedDate = df.format(document.getCreated());
                item.add(new Label("file.date", formattedDate));


                IModel<File> fileModel = new AbstractReadOnlyModel<File>() {
                    @Override
                    public File getObject() {
                        File tempFile;
                        try
                        {
                            tempFile = File.createTempFile("oasis-download-", ".tmp");
                            InputStream data = new ByteArrayInputStream(document.getContent());
                            Files.writeTo(tempFile, data);
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }

                        return tempFile;
                    }
                };

                final DownloadLink downloadLink = new DownloadLink("file.download", fileModel, document.getName());
                downloadLink.setCacheDuration(Duration.NONE).setDeleteAfterDownload(true);
                item.add(downloadLink);
                item.setVisible(fileModel.getObject().canRead());

                final Link deleteLink = new Link("file.delete") {
                    @Override
                    public void onClick() {
                        final Project project = getProject();
                        project.getDocuments().remove(document);
                        projectService.save(project);
                        WebSession.get().success(getString("message.delete.success"));
                        setResponsePage(getPage().getClass(), getPage().getPageParameters());
                    }
                };
                item.add(deleteLink);
            }
        };
        add(filesList);
    }

    private Set<Document> getDocumentsOfCurrentProject() {
        return getProject().getDocuments();
    }

    private void addUploadForm() {
        final FileUploadForm documentUploadForm = new FileUploadForm("form.document.upload") {
            @Override
            public void onAfterSubmit(FileUpload uploadedFile) {
                if(uploadedFile != null) {
                    Document document = new Document();
                    document.setName(uploadedFile.getClientFileName());
                    document.setContentType(FilenameUtils.getExtension(uploadedFile.getClientFileName()));
                    document.setSize(uploadedFile.getSize());
                    document.setUser(WebSession.get().getUser());
                    document.setContent(uploadedFile.getBytes());

                    documentService.save(document);
                    final Project project = projectService.findOne(getProjectId());
                    project.getDocuments().add(document);
                    projectService.save(project);
                    WebSession.get().success(getString("message.upload.success"));
                } else {
                    WebSession.get().error(getString("message.upload.error"));
                }
                setResponsePage(getPage().getClass(), getPage().getPageParameters());
            }
        };
        add(documentUploadForm);
    }
}
