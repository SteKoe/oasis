package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.XmlImport;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.component.form.upload.FileUploadField;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.session.WebSession;

public class UploadReferenceCriterionCatalogPage extends ReferenceCriterionPage {
    private static final Logger LOG = Logger.getLogger(UploadReferenceCriterionCatalogPage.class);

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    CriterionService criterionService;

    public UploadReferenceCriterionCatalogPage() {
        super();
    }

    public UploadReferenceCriterionCatalogPage(PageParameters parameters) {
        super(parameters);
    }

    public UploadReferenceCriterionCatalogPage(IModel<?> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form form = new Form("form") {
            private FileUploadField fileUploadField;

            @Override
            protected void onInitialize() {
                super.onInitialize();

                fileUploadField = new FileUploadField("upload");
                add(fileUploadField);
            }

            @Override
            protected void onSubmit() {
                super.onSubmit();

                FileUpload fileUpload = fileUploadField.getFileUpload();
                if(fileUpload != null) {
                    String clientFileName = fileUpload.getClientFileName();
                    if(FilenameUtils.getExtension(clientFileName).equals("xml")) {
                        try {
                            File tmpFile = File.createTempFile("oasis-import-", ".tmp");
                            FileUtils.writeByteArrayToFile(tmpFile, fileUpload.getBytes());

                            XmlImport xml = new XmlImport(tmpFile);
                            List<CriterionGroup> criterionGroups = xml.getCriterionGroups();
                            criterionGroupService.save(criterionGroups);
                            List<Criterion> criterions = xml.getCriterions();
                            criterionService.save(criterions);
                            WebSession.get().success(getString("message.upload.success"));
                        } catch(Exception e) {
                            LOG.error("Error uploading file.", e);
                            WebSession.get().error(getString("message.upload.error"));
                        }
                    } else {
                        WebSession.get().error(getString("message.filetype.notsupported"));
                    }
                } else {
                    WebSession.get().error(getString("message.upload.error"));
                }

                setResponsePage(getPage());
            }
        };
        add(form);
    }
}
