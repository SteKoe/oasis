package de.stekoe.idss.model;

import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.service.ProjectService;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class LoadableProjectModel extends LoadableDetachableModel<Project> {

    @SpringBean
    ProjectService projectService;

    private final String id;

    public LoadableProjectModel(String id) {
        this.id = id;
    }

    @Override
    protected Project load() {
        return projectService.findById(this.id);
    }
}
