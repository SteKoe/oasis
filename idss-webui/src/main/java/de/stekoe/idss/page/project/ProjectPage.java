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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

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
        addLabelProjectStatus();

        // Links
        addLinkUploadDocument();
        addLinkEditProject();
        addLinkEditProjectMember();
        addLinkEditProjectRoles();
        addLinkProjectDetails();
        addLinkSetOfCriteria();
        addLinkResult();
        addLinkSurveyPage();
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
    }

    private MarkupContainer addLabelProjectTitle() {
        return add(new Label("projectTitle", Model.of(getProject().getName())));
    }

    private MarkupContainer addLinkResult() {
        BookmarkablePageLink<ResultPage> resultLink = new BookmarkablePageLink<ResultPage>("link.result", ResultPage.class, getProjectIdPageParam());
        if(!ProjectStatus.FINISHED.equals(getProject().getProjectStatus())) {
            resultLink.setVisible(false);
        }
        if(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE)) {
            resultLink.setVisible(true);
        }
        return add(resultLink);
    }

    private MarkupContainer addLinkSetOfCriteria() {
        return add(new BookmarkablePageLink<CriteriaPageListPage>("link.setofcriteria.edit", CriteriaPageListPage.class, getProjectIdPageParam()));
    }

    private PageParameters getProjectIdPageParam() {
        return new PageParameters().add("projectId", getProjectId().toString());
    }

    private MarkupContainer addLinkProjectDetails() {
        return add(new BookmarkablePageLink<ProjectDetailsPage>("link.project.overview", ProjectDetailsPage.class, getProjectIdPageParam()));
    }

    private void addLinkUploadDocument() {
        final BookmarkablePageLink<ProjectUploadDocument> linkUploadPage = new BookmarkablePageLink<ProjectUploadDocument>("link.upload.document", ProjectUploadDocument.class, getProjectIdPageParam());
        add(linkUploadPage);
        linkUploadPage.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPLOAD_FILE));
    }

    private void addLabelProjectStatus() {
        final Label projectStatusLabel = new Label("project.status", Model.of(getString(getProject().getProjectStatus().getKey())));
        add(projectStatusLabel);
    }

    private void addLinkEditProjectRoles() {
        final BookmarkablePageLink<ProjectRoleListPage> editProjectRolesLink = new BookmarkablePageLink<ProjectRoleListPage>("editProjectRolesLink", ProjectRoleListPage.class, getProjectIdPageParam());
        add(editProjectRolesLink);
        editProjectRolesLink.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.MANAGE_ROLES));
    }

    private void addLinkEditProjectMember() {
        final BookmarkablePageLink<ProjectMemberListPage> addMemberLink = new BookmarkablePageLink<ProjectMemberListPage>("addMember", ProjectMemberListPage.class, getProjectIdPageParam());
        add(addMemberLink);
        addMemberLink.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.MANAGE_MEMBER));
    }

    private void addLinkEditProject() {
        final BookmarkablePageLink<ProjectEditPage> editProjectLink = new BookmarkablePageLink<ProjectEditPage>("editProject", ProjectEditPage.class, getProjectIdPageParam());
        add(editProjectLink);
        editProjectLink.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE));
    }
}
