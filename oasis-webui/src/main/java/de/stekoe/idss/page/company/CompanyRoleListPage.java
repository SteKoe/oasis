package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.service.CompanyService;
import de.stekoe.idss.session.WebSession;

public class CompanyRoleListPage extends CompanyPage {

    @Inject
    private CompanyService companyService;

    public CompanyRoleListPage(PageParameters pageParameters) {
        super(pageParameters);

        final Employee employee = getCompany().getEmployee(WebSession.get().getUser());

        Button openModalButton = new Button("link.add");
        add(openModalButton);

        add(new ListView<CompanyRole>("roles", getCompany().getRoles()) {
            @Override
            protected void populateItem(ListItem<CompanyRole> item) {
                CompanyRole role = item.getModelObject();
                CompanyRoleInfoBlock roleInfoBlock = new CompanyRoleInfoBlock("role", role);
                item.add(roleInfoBlock);
                roleInfoBlock.getDeleteLink().setVisible(!employee.hasRole(role));
            }
        });

        AddCompanyRoleModal addCompanyRoleModal = new AddCompanyRoleModal("add.companyrole.modal") {

            @Override
            public void onSave(CompanyRole companyRole) {
                // Set the company id of the selected company roles
                for(Permission p: companyRole.getPermissions()) {
                    p.setObjectId(getCompany().getId());
                }
                getCompany().getRoles().add(companyRole);
                companyService.save(getCompany());
                success("success");
            }
        };
        add(addCompanyRoleModal);
        addCompanyRoleModal.addOpenerAttributesTo(openModalButton);
    }
}
