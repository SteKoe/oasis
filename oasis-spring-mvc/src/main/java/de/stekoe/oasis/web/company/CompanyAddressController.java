package de.stekoe.oasis.web.company;

import de.stekoe.oasis.model.Address;
import de.stekoe.oasis.model.Company;
import de.stekoe.oasis.service.AddressService;
import de.stekoe.oasis.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyAddressController {

    @Autowired
    CompanyService companyService;

    @Autowired
    AddressService addressService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/{id}/address", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.oasis.model.PermissionType).MANAGE_ADDRESSES)")
    public ModelAndView listAddress(@PathVariable String id) {
        ModelAndView model = new ModelAndView("/company/address");
        Company company = companyService.findOne(id);
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        return model;
    }

    @RequestMapping(value = "/{id}/address/create", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.oasis.model.PermissionType).MANAGE_ADDRESSES)")
    public ModelAndView createAddress(@PathVariable String id) {
        ModelAndView model = new ModelAndView("/company/address_edit");
        Company company = companyService.findOne(id);
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("address", new Address());
        return model;
    }

    @RequestMapping(value = "/{id}/address/{aid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.oasis.model.PermissionType).MANAGE_ADDRESSES)")
    public ModelAndView listAddress(@PathVariable String id, @PathVariable String aid) {
        ModelAndView model = new ModelAndView("/company/address_edit");
        Company company = companyService.findOne(id);
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("address", company.getAddresses().stream().filter(address -> address.getId().equals(aid)).findAny().get());
        return model;
    }

    @RequestMapping(value = "/{id}/address", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.oasis.model.PermissionType).MANAGE_ADDRESSES)")
    public ModelAndView saveAddress(@PathVariable String id, @Valid Address address, BindingResult bindingResult) {
        Company company = companyService.findOne(id);

        ModelAndView model = new ModelAndView("/company/address_edit");

        if(bindingResult.hasErrors() == false) {
            if(address.getId().isEmpty()) {
                company.getAddresses().add(address);
                companyService.save(company);
            } else {
                addressService.save(address);
            }

            model.setViewName(String.format("redirect:/company/%s/address", id));
            return model;
        }

        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("address", address);
        return model;
    }

    @RequestMapping(value = "/{id}/address/{aid}/delete", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.oasis.model.PermissionType).MANAGE_ADDRESSES)")
    public String deleteAddress(@PathVariable String id, @PathVariable String aid, RedirectAttributes redirectAttributes) {
        Company company = companyService.findOne(id);

        if(company.getAddresses().size() == 1) {
            redirectAttributes.addFlashAttribute("flashError", "message.delete.error");
        } else {
            Optional<Address> addressToDelete = company.getAddresses().stream().filter(address -> address.getId().equals(aid)).findAny();
            if(addressToDelete.isPresent()) {
                company.getAddresses().remove(addressToDelete.get());
                companyService.save(company);

                redirectAttributes.addFlashAttribute("flashSuccess", "message.delete.success");
            } else {
                redirectAttributes.addFlashAttribute("flashError", "message.delete.error");
            }
        }


        return String.format("redirect:/company/%s/address", id);
    }
}
