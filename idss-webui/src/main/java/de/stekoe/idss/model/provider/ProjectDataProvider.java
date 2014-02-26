package de.stekoe.idss.model.provider;

import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.Iterator;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectDataProvider implements IDataProvider<Project> {

    @Inject
    ProjectService projectService;

    @Override
    public Iterator<? extends Project> iterator(long first, long count) {
        return projectService.findAllForUserPaginated(WebSession.get().getUser().getId(), (int) count, (int) first).iterator();
    }

    @Override
    public long size() {
        return projectService.findAllForUser(WebSession.get().getUser().getId()).size();
    }

    @Override
    public IModel<Project> model(final Project project) {
        return new Model(project);
    }

    @Override
    public void detach() {
    }
}
