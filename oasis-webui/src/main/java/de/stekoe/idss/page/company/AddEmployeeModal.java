package de.stekoe.idss.page.company;

import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;

public abstract class AddEmployeeModal extends AddOrInviteUserModal {

    @SpringBean
    private UserService userService;

    private String user;

    public AddEmployeeModal(String id) {
        super(id);

        header(Model.of(getString("label.project.add.member")));
    }

    public abstract void onSave(User existingUser);
}
