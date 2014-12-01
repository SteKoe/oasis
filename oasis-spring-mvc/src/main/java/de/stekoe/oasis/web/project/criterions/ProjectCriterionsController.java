package de.stekoe.oasis.web.project.criterions;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrderableUtil;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.oasis.web.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProjectCriterionsController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    private static int CRITERIONPAGES_PER_PAGE = 1;

    @RequestMapping(value = "/project/{pid}/criterions")
    public ModelAndView list(@PathVariable String pid, @RequestParam(required = false) Integer page) {
        if(page == null) {
            page = new Integer(0);
        }

        Project project = projectService.findOne(pid);

        long count = criterionPageService.countForProject(pid);


        PageRequest pageable = new PageRequest(page.intValue(), CRITERIONPAGES_PER_PAGE);
        List<CriterionPage> criterionPages = criterionPageService.findAllForProject(project.getId(), pageable);


        ModelAndView model = new ModelAndView("/project/criterions/list");
        model.addObject("project", project);
        model.addObject("pagination", new Pagination(count, CRITERIONPAGES_PER_PAGE, page.intValue()));
        model.addObject("criterionPages", criterionPages);
        model.addObject("pageTitle", project.getName());
        return model;
    }
}
