package de.stekoe.idss.page.project;

import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.IProjectService;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectDeletePage extends AuthUserPage {

    @Inject
    private IProjectService projectService;
}
