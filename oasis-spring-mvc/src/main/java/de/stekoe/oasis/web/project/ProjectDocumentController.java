package de.stekoe.oasis.web.project;

import de.stekoe.oasis.model.Document;
import de.stekoe.oasis.model.PermissionType;
import de.stekoe.oasis.model.Project;
import de.stekoe.oasis.service.DocumentService;
import de.stekoe.oasis.service.ProjectService;
import de.stekoe.oasis.service.UserService;
import de.stekoe.oasis.beans.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    PermissionManager permissionManager;

    @RequestMapping(value = "/project/{pid}/document", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).UPLOAD_FILE)")
    public ModelAndView list(@PathVariable String pid) {
        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("/project/document/list");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("document", new Document());

        return model;
    }

    @RequestMapping(value = "/project/{pid}/document/{did}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).UPLOAD_FILE)")
    public void download(@PathVariable String pid, @PathVariable String did, HttpServletResponse response) {
        try {
            Document document = documentService.findOne(did);
            response.setContentType(document.getContentType());
            response.setContentLength((int) document.getSize());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", document.getName());
            response.setHeader(headerKey, headerValue);

            ServletOutputStream outputStream = response.getOutputStream();
            FileCopyUtils.copy(document.getContent(), outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canDelete(String did, String pid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        boolean mayManageFiles = permissionManager.hasProjectPermission(principal, pid, PermissionType.UPLOAD_FILE);

        Document document = documentService.findOne(did);
        boolean fileUploadedByUser = document.getUser().getUsername().equals(principal.getUsername());
        if(fileUploadedByUser || mayManageFiles) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * ==== REST API ===================================================================================================
     */
    @RequestMapping(value = "/api/project/{pid}/document", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).UPLOAD_FILE)")
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
                documentService.save(document);

                Project project = projectService.findOne(pid);
                project.getDocuments().add(document);
                projectService.save(project);

                redirectAttributes.addFlashAttribute("flashSuccess", messageSource.getMessage("message.upload.success", null, locale));
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.upload.error", null, locale));
            }
        } else {
            redirectAttributes.addFlashAttribute("flashError", messageSource.getMessage("message.upload.no.file", null, locale));
        }
        return "redirect:/project/" + pid + "/document";
    }

    @RequestMapping(value = "/api/project/{pid}/document/{did}", method = RequestMethod.DELETE)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).UPLOAD_FILE)")
    public @ResponseBody String post(@PathVariable String pid, @PathVariable String did) {
        if(canDelete(did, pid)) {
            // Delete Document from Project
            Project project = projectService.findOne(pid);
            Set<Document> documents = project.getDocuments().stream().filter(document -> !document.getId().equals(did)).collect(Collectors.toSet());
            project.setDocuments(documents);
            projectService.save(project);

            // Delete the Document itself
            documentService.delete(did);
            return "{\"ok\":true}";
        } else {
            return "{\"ok\":false}";
        }
    }
}
