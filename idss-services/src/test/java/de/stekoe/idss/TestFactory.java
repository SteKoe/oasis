package de.stekoe.idss;

import java.util.UUID;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;

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
        return projectMember;
    }

    public static Project createProject() {
        final Project project = new Project();
        project.setName("Random Project Name");
        project.setDescription("Random Project Description");
        return project;
    }

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
