/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.page.project;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameAppender;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.ProjectStatus;
import de.stekoe.idss.page.project.criterion.ResultPage;
import de.stekoe.idss.page.project.criterion.SurveyPage;
import de.stekoe.idss.page.project.criterion.page.CriteriaPageListPage;
import de.stekoe.idss.service.ProjectService;

public abstract class ProjectPage extends AuthProjectPage {

    @SpringBean
    ProjectService projectService;

    public ProjectPage(PageParameters pageParameters) {
        super(pageParameters);

        addLabelProjectTitle();
//        addLabelProjectStatus();

        // Links
        addLinkUploadDocument();
        addLinkEditProject();
        addLinkEditProjectMember();
        addLinkEditProjectRoles();
        addLinkProjectDetails();
        addLinkSetOfCriteria();
        addLinkSurveyPage();
        addLinkResult();
    }

    private void addLinkSurveyPage() {
        BookmarkablePageLink<SurveyPage> surveyPage = new BookmarkablePageLink<SurveyPage>("link.survey", SurveyPage.class, getProjectIdPageParam());
        add(surveyPage);
        if(!ProjectStatus.INPROGRESS.equals(getProject().getProjectStatus())) {
            surveyPage.setVisible(false);
        }
        if(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE)) {
            surveyPage.setVisible(true);
        }
        if(isLinkActive(surveyPage)) {
            surveyPage.add(new CssClassNameAppender("active"));
        }
    }

    private void addLabelProjectTitle() {
        setTitle(getProject().getName());
    }

    private MarkupContainer addLinkResult() {
        BookmarkablePageLink<ResultPage> resultLink = new BookmarkablePageLink<ResultPage>("link.result", ResultPage.class, getProjectIdPageParam());
        if(!ProjectStatus.FINISHED.equals(getProject().getProjectStatus())) {
            resultLink.setVisible(false);
        }
        if(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE)) {
            resultLink.setVisible(true);
        }
        if(isLinkActive(resultLink)) {
            resultLink.add(new CssClassNameAppender("active"));
        }
        return add(resultLink);
    }

    private MarkupContainer addLinkSetOfCriteria() {
        BookmarkablePageLink<CriteriaPageListPage> link = new BookmarkablePageLink<CriteriaPageListPage>("link.setofcriteria.edit", CriteriaPageListPage.class, getProjectIdPageParam());
        if(isLinkActive(link)) {
            link.add(new CssClassNameAppender("active"));
        }
        return add(link);
    }

    private PageParameters getProjectIdPageParam() {
        return new PageParameters().add("projectId", getProjectId().toString());
    }

    private MarkupContainer addLinkProjectDetails() {
        BookmarkablePageLink<ProjectDetailsPage> link = new BookmarkablePageLink<ProjectDetailsPage>("link.project.overview", ProjectDetailsPage.class, getProjectIdPageParam());
        if(isLinkActive(link)) {
            link.add(new CssClassNameAppender("active"));
        }
        return add(link);
    }

    private void addLinkUploadDocument() {
        final BookmarkablePageLink<ProjectUploadDocument> linkUploadPage = new BookmarkablePageLink<ProjectUploadDocument>("link.upload.document", ProjectUploadDocument.class, getProjectIdPageParam());
        add(linkUploadPage);
        linkUploadPage.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPLOAD_FILE));
        if(isLinkActive(linkUploadPage)) {
            linkUploadPage.add(new CssClassNameAppender("active"));
        }
    }

    private void addLabelProjectStatus() {
        final Label projectStatusLabel = new Label("project.status", Model.of(getString(getProject().getProjectStatus().getKey())));
        add(projectStatusLabel);
    }

    private void addLinkEditProjectRoles() {
        final BookmarkablePageLink<ProjectRoleListPage> editProjectRolesLink = new BookmarkablePageLink<ProjectRoleListPage>("editProjectRolesLink", ProjectRoleListPage.class, getProjectIdPageParam());
        add(editProjectRolesLink);
        editProjectRolesLink.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.MANAGE_ROLES));
        if(isLinkActive(editProjectRolesLink)) {
            editProjectRolesLink.add(new CssClassNameAppender("active"));
        }
    }

    private void addLinkEditProjectMember() {
        final BookmarkablePageLink<ProjectMemberListPage> addMemberLink = new BookmarkablePageLink<ProjectMemberListPage>("addMember", ProjectMemberListPage.class, getProjectIdPageParam());
        add(addMemberLink);
        addMemberLink.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.MANAGE_MEMBER));
        if(isLinkActive(addMemberLink)) {
            addMemberLink.add(new CssClassNameAppender("active"));
        }
    }

    private void addLinkEditProject() {
        final BookmarkablePageLink<ProjectEditPage> editProjectLink = new BookmarkablePageLink<ProjectEditPage>("editProject", ProjectEditPage.class, getProjectIdPageParam());
        add(editProjectLink);
        editProjectLink.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE));
        if(isLinkActive(editProjectLink)) {
            editProjectLink.add(new CssClassNameAppender("active"));
        }
    }

    private boolean isLinkActive(BookmarkablePageLink<? extends WebPage> link) {
        return link.linksTo(getPage());
    }
}
