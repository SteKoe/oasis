package de.stekoe.idss.page.project;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;
import org.joda.time.Days;

import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.page.HomePage;

public class ProjectDetailsPage extends ProjectPage {
    public ProjectDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        setTitle(getProject().getName());

        addDescription();
//        addDateInformation();
        addListOfProjectMember();
    }

    private void addDateInformation() {
        final Date projectStartDate = getProject().getProjectStartDate();
        final Date projectEndDate = getProject().getProjectEndDate();

        final Label projectStartDateLabel = new Label("projectStartDate", projectStartDate);
        add(projectStartDateLabel);
        projectStartDateLabel.setVisible(false);

        final Label projectEndDateLabel = new Label("projectEndDate", projectEndDate);
        add(projectEndDateLabel);
        projectEndDateLabel.setVisible(false);

        double progress = 0;
        if (projectEndDate == null) {
            projectEndDateLabel.setVisible(false);
        } else {
            final Days totalProjectDays = Days.daysBetween(new DateTime(projectStartDate.getTime()), new DateTime(projectEndDate.getTime()));
            final Days passedDays = Days.daysBetween(new DateTime(projectStartDate.getTime()), new DateTime());

            if (passedDays.getDays() > 0) {
                progress = (double) passedDays.getDays() / (double) totalProjectDays.getDays();
                progress *= 100;
            }
        }

        final WebMarkupContainer progressBar = new WebMarkupContainer("progressBar");
        progressBar.add(new AttributeModifier("style", "width: " + Math.round(progress) + "%"));
        add(progressBar);

        progressBar.add(new Label("progress", Math.round(progress)));
    }

    private void addDescription() {
        String description = getProject().getDescription();
        if (StringUtils.isEmpty(description)) {
            description = getString("label.description.na");
        }
        final Label descriptionText = new Label("descriptionText", Model.of(description));
        add(descriptionText);
        descriptionText.setEscapeModelStrings(false);
    }

    private void addListOfProjectMember() {
        final Collection<ProjectMember> projectTeam = getProject().getProjectTeam();
        final List<ProjectMember> projectMember = (List<ProjectMember>) CollectionUtils.select(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return true;
            }
        });

        String numOfPersonsString = getString("label.project.numOfPersons");
        int projectMemberCount = projectMember.size();
        add(new Label("projectMemberCount", MessageFormat.format(numOfPersonsString, projectMemberCount)));

        add(new ListView<ProjectMember>("projectMemberItem", projectMember) {
            @Override
            protected void populateItem(ListItem<ProjectMember> item) {
                ProjectMember pm = item.getModelObject();

                final BookmarkablePageLink<HomePage> userDetailsLink = new BookmarkablePageLink<HomePage>("projectMemberItemLink", HomePage.class);

                String usersName = null;

                User user = pm.getUser();
                UserProfile profile = user.getProfile();
                if(profile != null) {
                    usersName = profile.getFullName();
                }

                if(StringUtils.isBlank(usersName)) {
                    usersName = user.getUsername();
                }

                Label userName = new Label("projectMemberName", Model.of(usersName));
                userDetailsLink.add(userName);

                Label usersRole = new Label("projectMemberRole", Model.of(pm.getProjectRole()));
                userDetailsLink.add(usersRole);

                item.add(userDetailsLink);
            }
        });
    }

}
