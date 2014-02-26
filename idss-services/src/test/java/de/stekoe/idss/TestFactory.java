package de.stekoe.idss;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;

import java.util.UUID;

public class TestFactory {

    public static User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(username.toLowerCase().replace(" ", "") + "@example.com");
        user.setPassword(password);
        return user;
    }

    public static User createUser(String username) {
        return createUser(username, "secret");
    }

    public static User createRandomUser() {
        return createUser(createUUID(), "secret");
    }

    public static ProjectMember createProjectMember() {
        final ProjectMember projectMember = new ProjectMember();
        projectMember.setId(createUUID());
        return projectMember;
    }

    public static Project createProject() {
        final Project project = new Project();
        project.setId(createUUID());
        project.setName("Random Project Name");
        project.setDescription("Random Project Description");
        return project;
    }

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
