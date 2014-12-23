package de.stekoe.oasis.web.company;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.AddressService;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.CompanyService;
import de.stekoe.idss.service.UserService;
import de.stekoe.oasis.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyEmployeeController {

    Logger logger = Logger.getLogger(CompanyEmployeeController.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    AuthService authService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/{id}/employee", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public ModelAndView listEmployee(@PathVariable String id) {
        Company company = companyService.findOne(id);

        ModelAndView model = new ModelAndView("/company/employee");
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        return model;
    }

    @RequestMapping(value = "/{id}/employee/{eid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public ModelAndView listEmployee(@PathVariable String id, @PathVariable String eid) {
        Company company = companyService.findOne(id);
        Employee employee = company.getEmployees().stream().map(emp -> (Employee) emp).filter(emp -> emp.getId().equals(eid)).findAny().get();

        EmployeeDescriptor employeeDesc = new EmployeeDescriptor();
        employeeDesc.setId(employee.getId());
        employeeDesc.setEmail(employee.getUser().getEmail());
        employeeDesc.setRole(employee.getRole() != null ? employee.getRole().getId() : null);

        ModelAndView model = new ModelAndView("/company/employee_edit");
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("employee", employeeDesc);
        model.addObject("companyRoles", company.getRoles());
        return model;
    }

    @RequestMapping(value = "/{cid}/employee", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #cid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public String saveEmployee(@PathVariable String cid, @Valid EmployeeDescriptor employeeDescriptor, BindingResult bindingResult, HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
        Company company = companyService.findOne(cid);
        Employee existingEmployee = company.getEmployee(employeeDescriptor.getEmail());
        CompanyRole role = company.getRoles().stream().filter(r -> r.getId().equals(employeeDescriptor.getRole())).findFirst().get();

        // Edit mode
        if(employeeDescriptor.getId().isEmpty() == false) {
            existingEmployee.setRole(role);
            companyService.save(company);
            redirectAttributes.addFlashAttribute("flashSuccess", "message.edit.success");
            return String.format("redirect:/company/%s/employee", cid);
        }

        // Check if there already exists an employee with given mail:
        if(existingEmployee != null && employeeDescriptor.getId().isEmpty()) {
            redirectAttributes.addFlashAttribute("flashError", "message.employee.exists");
            return String.format("redirect:/company/%s/employee", cid);
        }

        if(bindingResult.hasErrors() == false) {
            boolean isNewUser = false;
            String plainPassword = RandomStringUtils.randomAlphanumeric(7);

            User user = userService.findByEmail(employeeDescriptor.getEmail());
            if(user == null) {
                isNewUser = true;

                user = new User();
                user.setEmail(employeeDescriptor.getEmail());
                user.setUsername(employeeDescriptor.getEmail());
                user.setPassword(authService.hashPassword(plainPassword));

                try {
                    userService.save(user);
                } catch (Exception e) {
                    logger.error("Error saving user!", e);
                }
            }

            Employee employee = new Employee();
            employee.setUser(user);
            employee.setRole(role);
            employee.setEmployeeStatus(UserStatus.ACTIVATED);

            company.getEmployees().add(employee);


            if(isNewUser) {
                try {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    String username = auth.getName();
                    User invitingUser = userService.findByUsername(username);

                    mailService.sendCompanyInviteMail(invitingUser, user, plainPassword, company, request, locale);
                } catch (MessagingException e) {
                    logger.error("Could not send company invitation email!", e);
                }
            }

            companyService.save(company);
        }

        return String.format("redirect:/company/%s/employee", cid);
    }

    @RequestMapping(value = "/{id}/employee/create", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public ModelAndView createEmployee(@PathVariable String id) {
        ModelAndView model = new ModelAndView("/company/employee_edit");
        Company company = companyService.findOne(id);
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("employee", new EmployeeDescriptor());
        model.addObject("companyRoles", company.getRoles());
        return model;
    }

    @RequestMapping(value = "/{cid}/employee/{eid}/activate", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #cid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public String activateEmployee(@PathVariable String cid, @PathVariable String eid, HttpServletRequest request) {
        Company company = companyService.findOne(cid);
        Optional<Employee> first = company.getEmployees().stream().filter(emp -> emp.getId().equals(eid)).findFirst();
        if(first.isPresent()) {
            Employee employee = first.get();
            employee.setEmployeeStatus(UserStatus.ACTIVATED);
            companyService.save(company);
        }

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/{cid}/employee/{eid}/delete", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #cid, T(de.stekoe.idss.model.PermissionType).MANAGE_MEMBER)")
    public String deleteEmployee(@PathVariable String cid, @PathVariable String eid) {
        Company company = companyService.findOne(cid);
        Optional<Employee> first = company.getEmployees().stream().filter(emp -> emp.getId().equals(eid)).findFirst();
        if(first.isPresent()) {
            Employee employee = first.get();
            company.getEmployees().remove(employee);
            companyService.save(company);
        }

        return String.format("redirect:/company/%s/employee", cid);
    }
}
