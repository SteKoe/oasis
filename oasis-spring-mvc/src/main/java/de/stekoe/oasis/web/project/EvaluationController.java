package de.stekoe.oasis.web.project;

import de.stekoe.oasis.model.CriterionPage;
import de.stekoe.oasis.model.Project;
import de.stekoe.oasis.model.User;
import de.stekoe.oasis.model.UserChoices;
import de.stekoe.oasis.service.CriterionPageService;
import de.stekoe.oasis.service.ProjectService;
import de.stekoe.oasis.service.UserChoicesService;
import de.stekoe.oasis.service.UserService;
import de.stekoe.oasis.web.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EvaluationController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    UserChoicesService userChoicesService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/project/{pid}/evaluation", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, @PathVariable String pid) {
        Project project = projectService.findOne(pid);

        int pageNum = ServletRequestUtils.getIntParameter(request, "page", 0);

        List<CriterionPage> criterionPages = criterionPageService.findAllForProject(project.getId(), new PageRequest(pageNum, 1));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        UserChoices userChoices = userChoicesService.findByUserAndProject(user, project);

        if(userChoices == null) {
            userChoices = new UserChoices();
        }

        ModelAndView model = new ModelAndView("/project/evaluation/list");
        model.addObject("project", project);
        model.addObject("count", criterionPageService.countForProject(pid));
        model.addObject("pageTitle", project.getName());
        model.addObject("pagination", new Pagination(criterionPageService.countForProject(pid), 1, pageNum));
        model.addObject("criterionPages", criterionPages);
        model.addObject("userChoices", userChoices);
        return model;
    }

    @RequestMapping(value = "/project/{pid}/evaluation", method = RequestMethod.POST)
    public String saveEvaluation(Model model, UserChoices userChoices, @PathVariable String pid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Project project = projectService.findOne(pid);
        User user = userService.findByUsername(username);
        UserChoices uc = userChoicesService.findOne(userChoices.getId());
        if(uc == null) {
            uc = new UserChoices();
        }
        uc.setUser(user);
        uc.setProject(project);

        final UserChoices finalUc = uc;
        userChoices.getUserChoices().keySet().forEach(key -> {
            userChoices.getUserChoices().get(key).setUserChoices(finalUc);
            finalUc.getUserChoices().putAll(userChoices.getUserChoices());
        });

        userChoicesService.save(finalUc);

        return String.format("redirect:/project/%s/evaluation", pid);
    }
}
