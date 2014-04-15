package de.stekoe.idss.wicket.model;

import javax.inject.Inject;

import org.apache.wicket.model.Model;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.service.CompanyService;

public class CompanyModel extends Model<Company> {

    @Inject
    CompanyService companyService;

    private String id;
    private Company company;

    public CompanyModel() {
        this.id = null;
    }

    public CompanyModel(String id) {
        this.id = id;
    }

    @Override
    public Company getObject() {
        if(this.company != null) {
            return this.company;
        }

        if(this.id != null) {
            return companyService.findOne(this.id);
        } else {
            return null;
        }
    }

    @Override
    public void detach() {
        this.company = null;
    }

    public void setId(String id) {
        this.id = id;
    }
}
