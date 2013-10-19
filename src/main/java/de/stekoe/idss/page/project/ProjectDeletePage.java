package de.stekoe.idss.page.project;

import de.stekoe.idss.page.auth.annotation.ProjectLeaderOnly;
import de.stekoe.idss.service.IProjectService;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@ProjectLeaderOnly
public class ProjectDeletePage extends ProjectPage {

    @Inject
    private IProjectService projectService;

    public ProjectDeletePage(PageParameters pageParameters) {
        super(pageParameters);
    }
}
