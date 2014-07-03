package de.stekoe.idss.page.project;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.data.domain.PageRequest;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

public class ProjectDataProvider implements IDataProvider<Project> {

    @Inject
    ProjectService projectService;

    @Override
    public Iterator<? extends Project> iterator(long first, long count) {
        return projectService.findAll(new PageRequest((int)first, (int)count)).iterator();
    }

    @Override
    public long size() {
        return projectService.findByUser(WebSession.get().getUser().getId()).size();
    }

    @Override
    public IModel<Project> model(final Project project) {
        return new Model(project);
    }

    @Override
    public void detach() {
        // NOP
    }
}
