package de.stekoe.oasis.web.company;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.service.AddressService;
import de.stekoe.idss.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    AddressService addressService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping
    public String index(RedirectAttributes redirectAttributes, Locale locale) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Company company = companyService.findByUsername(username);
        if(company != null) {
            return String.format("redirect:/company/%s", company.getId());
        }

        redirectAttributes.addAttribute("flashError",messageSource.getMessage("message.user.no.company", null, locale));
        return "redirect:/";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasCompanyPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).READ)")
    public ModelAndView index(@PathVariable String id) {
        Company company = companyService.findOne(id);

        Map<CompanyRole, List<Employee>> groupedEmployees = new LinkedHashMap<>();
        company.getRoles().forEach(companyRole -> {
            List<Employee> employeesByRole = company.getEmployeesByRole(companyRole);
            groupedEmployees.put(companyRole, employeesByRole);
        });

        ModelAndView model = new ModelAndView("/company/details");
        model.addObject("company", company);
        model.addObject("pageTitle", company.getName());
        model.addObject("employeesByRole", groupedEmployees);
        return model;
    }
}
