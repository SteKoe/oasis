package de.stekoe.idss;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.service.UserService;
import org.apache.log4j.Logger;
import org.apache.wicket.ajax.json.JSONException;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.RequestParam;
import org.wicketstuff.rest.resource.gson.GsonRestResource;
import org.wicketstuff.rest.utils.http.HttpMethod;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class RestRoutes extends GsonRestResource {

    private static final Logger LOG = Logger.getLogger(RestRoutes.class);

    @Inject
    UserService userService;

    @MethodMapping(value = "/user/find/name", httpMethod = HttpMethod.GET)
    public List<UserPojo> findByUsername(@RequestParam("username") String username) throws JSONException {
        final List<User> users = userService.findAllByUsername(username);

        List<UserPojo> userPojoList = new ArrayList<UserPojo>();

        for (User user : users) {
            userPojoList.add(new UserPojo(user));
        }

        return userPojoList;
    }

    private class UserPojo {
        private String id;
        private String username;
        private UserStatus userStatus;
        private String label;
        private String value;

        public UserPojo() {
            // NOP
        }

        public UserPojo(User user) {
            this.id = user.getId();
            this.username = this.label = this.value = user.getUsername();
            this.userStatus = user.getUserStatus();
        }
    }
}
