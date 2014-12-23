package de.stekoe.oasis.beans;

import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.service.CompanyService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class PermissionManager {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    CompanyService companyService;

    public boolean hasProjectPermission(User user, String id, PermissionType permissionType) {
        de.stekoe.idss.model.User usr = userService.findByUsername(user.getUsername());
        if(usr != null) {
            boolean result = projectService.isAuthorized(usr.getId(), id, permissionType);
            return result;
        }

        return false;
    }

    public boolean hasCompanyPermission(String username, String id, PermissionType permissionType) {
        de.stekoe.idss.model.User usr = userService.findByUsername(username);
        if(usr != null) {
            boolean result = companyService.isAuthorized(usr.getId(), id, permissionType);
            return result;
        }

        return false;
    }

    public boolean hasCompanyPermission(User user, String id, PermissionType permissionType) {
        return hasCompanyPermission(user.getUsername(), id, permissionType);
    }
}
