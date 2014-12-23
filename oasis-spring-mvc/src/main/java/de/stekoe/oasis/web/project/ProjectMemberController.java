package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.ProjectMemberService;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProjectMemberController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRoleService projectRoleService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectMemberService projectMemberService;

    @RequestMapping(value = "/project/{pid}/member", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public ModelAndView edit(@PathVariable String pid) {
        Project project = projectService.findOne(pid);

        Set<User> projectMembers = new HashSet<>();
        project.getProjectTeam().stream().map(member -> (ProjectMember)member).forEach(member -> projectMembers.add(member.getUser()));

        List<User> userList = userService.findAll();
        userList = userList.stream().map(user -> (User)user).filter(user -> !projectMembers.contains(user)).collect(Collectors.toList());

        ModelAndView model = new ModelAndView("/project/member/list");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("users", userList);
        model.addObject("projectRoles", project.getProjectRoles());
        return model;
    }

    /*
     * ==== REST API ===================================================================================================
     */
    @RequestMapping(value = "/api/project/{pid}/member", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    @ResponseBody
    public Set<ProjectMemberDescriptor> apiList(@PathVariable String pid) {
        Set<ProjectMemberDescriptor> pm = new HashSet<>();

        Project project = projectService.findOne(pid);
        project.getProjectTeam().stream().map(member -> (ProjectMember) member).forEach(member -> {
            pm.add(new ProjectMemberDescriptor(member.getId(), member.getUser().getUsername(), member.getProjectRole().getName()));
        });

        return pm;
    }

    @RequestMapping(value = "/api/project/{pid}/member/{mid}", method = RequestMethod.DELETE, produces="application/json")
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    @ResponseBody
    public String delete(@PathVariable String pid, @PathVariable String mid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Project project = projectService.findOne(pid);

        ProjectMember deleteCandidate = project.getProjectTeam().stream().filter(member -> member.getId().equals(mid)).findFirst().get();
        if(deleteCandidate.getUser().getUsername().equals(currentUsername)) {
            return "{\"ok\":false,\"reason\":\"You cannot delete yourself!\"}";
        }

        Set<ProjectMember> pm = project.getProjectTeam().stream().filter(member -> !member.equals(deleteCandidate)).collect(Collectors.toSet());
        project.setProjectTeam(pm);

        projectService.save(project);

        return "{\"ok\":true}";
    }

    @RequestMapping(value = "/api/project/{pid}/member", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    @ResponseBody
    public String post(@PathVariable String pid, @RequestBody MultiValueMap<String,String> body) {
        String userid = body.getFirst("userid");
        String projectRole = body.getFirst("projectRole");

        Project project = projectService.findOne(pid);
        ProjectRole pr = project.getProjectRoles()
                .stream().filter(role -> role.getName().equals(projectRole))
                .findFirst().get();

        ProjectMember projectMember = new ProjectMember();
        projectMember.setUser(userService.findOne(userid));
        projectMember.setProjectRole(pr);

        project.getProjectTeam().add(projectMember);

        projectService.save(project);

        return "{\"ok\":true}";
    }

    @RequestMapping(value = "/api/project/{pid}/member/{mid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    @ResponseBody
    public ProjectMemberDescriptor get(@PathVariable String pid, @PathVariable String mid) {
        ProjectMember projectMember = projectMemberService.findOne(mid);
        return new ProjectMemberDescriptor(projectMember);
    }

    @RequestMapping(value = "/api/project/{pid}/member/{mid}", method = RequestMethod.PUT)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    @ResponseBody
    public String put(@PathVariable String pid, @PathVariable String mid, @RequestBody MultiValueMap<String,String> body) {
        String projectRole = body.getFirst("projectRole");

        Project project = projectService.findOne(pid);
        Optional<ProjectRole> match = project.getProjectRoles()
                .stream().filter(role -> role.getName().equals(projectRole))
                .findFirst();

        ProjectMember projectMember = projectMemberService.findOne(mid);
        projectMember.setProjectRole(match.get());

        projectMemberService.save(projectMember);

        return "{\"ok\":true}";
    }
}
