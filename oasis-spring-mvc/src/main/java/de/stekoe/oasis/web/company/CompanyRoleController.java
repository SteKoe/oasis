package de.stekoe.oasis.web.company;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.CompanyRoleService;
import de.stekoe.idss.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyRoleController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRoleService companyRoleService;

    @RequestMapping(value = "/{id}/role", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    public ModelAndView listRole(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        ModelAndView model = new ModelAndView("/company/role");
        Company company = companyService.findOne(id);
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("employee", company.getEmployee(username));
        return model;
    }

    @RequestMapping(value = "/{id}/role", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    public ModelAndView saveRole(@PathVariable String id, @Valid CompanyRoleDescriptor companyRoleDescriptor, BindingResult bindingResult) {
        Company company = companyService.findOne(id);

        ModelAndView model = new ModelAndView("/company/role_edit");

        if(bindingResult.hasErrors() == false) {
            CompanyRole companyRole;
            if(companyRoleDescriptor.getId().isEmpty() == false) {
                companyRole = company.getRoles().stream().filter(role -> role.getId().equals(companyRoleDescriptor.getId())).findAny().get();
            } else {
                companyRole = new CompanyRole();
            }

            companyRole.setName(companyRoleDescriptor.getName());
            if(companyRoleDescriptor.getPermissions() != null) {
                companyRoleDescriptor.getPermissions().stream().forEach(permission -> {
                    PermissionType permissionType = PermissionType.valueOf(permission);
                    companyRole.getPermissions().add(new Permission(PermissionObject.COMPANY, permissionType, id));
                });
            }

            if(companyRoleDescriptor.getId().isEmpty() == false) {
                companyRoleService.save(companyRole);
            } else {
                company.getRoles().add(companyRole);
                companyService.save(company);
            }
            model.setViewName(String.format("redirect:/company/%s/role", id));
            return model;
        }

        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("role", companyRoleDescriptor);
        return model;
    }

    @RequestMapping(value = "/{id}/role/create", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    public ModelAndView createRole(@PathVariable String id) {
        Company company = companyService.findOne(id);
        List<Permission> allPermissions = new ArrayList<>();
        PermissionType.forCompany().stream().map(pt -> (PermissionType)pt).forEach(pt -> {
            Permission permission = new Permission(PermissionObject.COMPANY, pt, id);
            allPermissions.add(permission);
        });

        ModelAndView model = new ModelAndView("/company/role_edit");
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("role", new CompanyRoleDescriptor());
        model.addObject("allPermissions", allPermissions);
        return model;
    }

    @RequestMapping(value = "/{id}/role/{rid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    public ModelAndView listRole(@PathVariable String id, @PathVariable String rid) {
        Company company = companyService.findOne(id);
        CompanyRole companyRole = company.getRoles().stream().filter(role -> role.getId().equals(rid)).findAny().get();
        List<Permission> allPermissions = new ArrayList<>();
        PermissionType.forCompany().stream().map(pt -> (PermissionType)pt).forEach(pt -> {
            Permission permission = new Permission(PermissionObject.COMPANY, pt, id);
            allPermissions.add(permission);
        });

        ModelAndView model = new ModelAndView("/company/role_edit");
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("role", new CompanyRoleDescriptor(companyRole));
        model.addObject("allPermissions", allPermissions);
        return model;
    }

    @RequestMapping(value = "/{id}/role/{rid}/delete", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    public String deleteRole(@PathVariable String id, @PathVariable String rid, RedirectAttributes redirectAttributes) {
        Company company = companyService.findOne(id);

        if(company.getRoles().size() == 1) {
            redirectAttributes.addFlashAttribute("flashError", "message.delete.error");
        } else {
            Optional<CompanyRole> roleToDelete = company.getRoles().stream().filter(role -> role.getId().equals(rid)).findAny();


            if(roleToDelete.isPresent()) {
                long count = company.getEmployees().stream().map(emp -> (Employee) emp).filter(emp -> emp.getRole().equals(roleToDelete.get())).count();
                if(count > 0) {
                    redirectAttributes.addFlashAttribute("flashError", "message.role.is.assigned.error");
                } else {
                    company.getRoles().remove(roleToDelete.get());
                    companyService.save(company);

                    redirectAttributes.addFlashAttribute("flashSuccess", "message.delete.success");
                }
            } else {
                redirectAttributes.addFlashAttribute("flashError", "message.delete.error");
            }
        }


        return String.format("redirect:/company/%s/role", id);
    }
}
