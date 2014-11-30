package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.DocumentService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class ProjectDocumentController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    DocumentService documentService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/project/{pid}/document", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).UPLOAD_FILE)")
    public ModelAndView list(@PathVariable String pid) {
        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("/project/document/list");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("document", new Document());

        return model;
    }

    /*
     * ==== REST API ===================================================================================================
     */
    @RequestMapping(value = "/api/project/{pid}/document", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).UPLOAD_FILE)")
    public String post(@PathVariable String pid, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes, Locale locale) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(!file.isEmpty()) {
            try {
                Document document = new Document();
                document.setSize(file.getSize());
                document.setName(file.getOriginalFilename());
                document.setContent(file.getBytes());
                document.setContentType(file.getContentType());
                document.setUser(userService.findByUsername(username));

//            documentService.save(document);
//
//            Project project = projectService.findOne(pid);
//            project.getDocuments().add(document);
//            projectService.save(project);

                redirectAttributes.addFlashAttribute("flashSuccess", messageSource.getMessage("message.upload.success", null, locale));
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.upload.error", null, locale));
            }
        } else {
            redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.upload.no.file", null, locale));
        }
        return "redirect:/project/" + pid + "/document";
    }
}
