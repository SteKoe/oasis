package de.stekoe.idss.page.project;

import de.stekoe.idss.component.form.upload.DocumentUploadForm;
import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.DocumentService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;


/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectUploadDocument extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private DocumentService documentService;

    public ProjectUploadDocument(PageParameters pageParameters) {
        super(pageParameters);

        addUploadForm();

        final ListView<Document> filesList = new ListView<Document>("files.list", new ArrayList<Document>(getProject().getDocuments())) {
            @Override
            protected void populateItem(ListItem<Document> item) {
                final Document document = item.getModelObject();

                item.add(new Label("file.name", document.getName()));
                item.add(new Label("file.size", Bytes.bytes(document.getSize()).toString(WebSession.get().getLocale())+"B"));
                item.add(new Label("file.type", document.getContentType()));

                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, WebSession.get().getLocale());
                String formattedDate = df.format(document.getCreated());
                item.add(new Label("file.date", formattedDate));


                IModel<File> fileModel = new AbstractReadOnlyModel<File>() {
                    @Override
                    public File getObject() {
                        return new File(documentService.getAbsolutePath(document.getId()));
                    }
                };
                final DownloadLink downloadLink = new DownloadLink("file.download", fileModel, document.getName());
                item.add(downloadLink);
                item.setVisible(fileModel.getObject().canRead());

                final Link deleteLink = new Link("file.delete") {
                    @Override
                    public void onClick() {
                        final Project project = getProject();
                        project.getDocuments().remove(document);
                        projectService.save(project);
                        WebSession.get().success("The document '" + document.getName() + "' has been removed from project!");
                        setResponsePage(getPage().getClass(), getPage().getPageParameters());
                    }
                };
                item.add(deleteLink);
            }
        };
        add(filesList);
    }

    private void addUploadForm() {
        final DocumentUploadForm documentUploadForm = new DocumentUploadForm("form.document.upload") {
            @Override
            public void onAfterSubmit(Document document) {
                final Project project = projectService.findById(getProjectId());
                project.getDocuments().add(document);
                projectService.save(project);
                setResponsePage(getPage().getClass(), getPage().getPageParameters());
            }
        };
        add(documentUploadForm);
    }
}
