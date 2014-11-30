package de.stekoe.oasis.beans;

import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class PermissionManager {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    public boolean hasProjectPermission(User user, String id, PermissionType permissionType) {
        de.stekoe.idss.model.User usr = userService.findByUsername(user.getUsername());
        if(usr != null) {
            boolean result = projectService.isAuthorized(usr.getId(), id, permissionType);
            return result;
        }

        return false;
    }
}
