package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.*;
import de.stekoe.idss.repository.UserChoicesRepository;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import de.stekoe.oasis.web.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EvaluationController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    UserChoicesRepository userChoicesRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/project/{pid}/evaluation", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable String pid) {
        Project project = projectService.findOne(pid);
        List<CriterionPage> criterionPages = criterionPageService.findAllForProject(project.getId(), new PageRequest(0, 10));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        UserChoices userChoices = userChoicesRepository.findByUserAndProject(user, project);

        if(userChoices == null) {
            userChoices = new UserChoices();
        }

        criterionPages.forEach(page -> {
            // Look for all criterions which are direct members of the current page
            List<SingleScaledCriterion> criterions = page.getPageElements()
                    .stream()
                    .filter(pe -> pe instanceof SingleScaledCriterion)
                    .map(ssc -> (SingleScaledCriterion)ssc)
                    .collect(Collectors.toList());

            // Now look for all criterions which are inside a group
            List<SingleScaledCriterion> criterionsOfGroups = page.getPageElements()
                    .stream()
                    .filter(pe -> pe instanceof CriterionGroup)
                    .map(pe -> (CriterionGroup) pe)
                    .map(cg -> cg.getCriterions())
                    .filter(c -> c instanceof SingleScaledCriterion)
                    .map(ssc -> (SingleScaledCriterion)ssc)
                    .collect(Collectors.toList());

            criterions.addAll(criterionsOfGroups);
        });

        ModelAndView model = new ModelAndView("/project/evaluation/list");
        model.addObject("project", project);
        model.addObject("count", criterionPageService.countForProject(pid));
        model.addObject("pageTitle", project.getName());
        model.addObject("pagination", new Pagination(1, 1, 0));
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

        userChoices.setUser(user);
        userChoices.setProject(project);
        userChoices.getUserChoices().keySet().forEach(key -> {
            userChoices.getUserChoices().get(key).setUserChoices(userChoices);
        });

        userChoicesRepository.save(userChoices);

        return String.format("redirect:/project/%s/evaluation", pid);
    }
}
