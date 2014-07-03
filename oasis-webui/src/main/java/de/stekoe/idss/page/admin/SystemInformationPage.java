package de.stekoe.idss.page.admin;

import javax.inject.Inject;

import de.stekoe.idss.page.AuthAdminPage;
import de.stekoe.idss.service.ConfigurationService;

public class SystemInformationPage extends AuthAdminPage {

    @Inject
    ConfigurationService configurationService;

    public SystemInformationPage() {
        configurationService.getConfiguration();
    }
}
