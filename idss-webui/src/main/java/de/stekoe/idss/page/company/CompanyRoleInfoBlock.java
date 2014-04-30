package de.stekoe.idss.page.company;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Permission;

public class CompanyRoleInfoBlock extends Panel {

    private final CompanyRole companyRole;
    private final Link linkDeleteRole;

    public CompanyRoleInfoBlock(String wicketId, CompanyRole companyRole) {
        super(wicketId);
        this.companyRole = companyRole;

        add(new Label("role.name", companyRole.getName()));

        StringBuilder permString = new StringBuilder();

        List<String> permissions = new ArrayList<String>();
        for (Permission p : companyRole.getPermissions()) {
            final String permissionKey = MessageFormat.format(getString(p.getPermissionType().getKey()), getString("label.company"));
            permissions.add(permissionKey);
        }
        add(new Label("permissions", StringUtils.join(permissions, ", ")));

        linkDeleteRole = new Link("link.role.delete") {
            @Override
            public void onClick() {

            }
        };
        add(linkDeleteRole);
    }

    public Link getDeleteLink() {
        return this.linkDeleteRole;
    }

}
