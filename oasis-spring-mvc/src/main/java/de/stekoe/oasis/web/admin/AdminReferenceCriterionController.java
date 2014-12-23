package de.stekoe.oasis.web.admin;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.oasis.web.project.criterions.XmlImport;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminReferenceCriterionController {

    @Autowired
    CriterionGroupService criterionGroupService;

    @RequestMapping(value = "/admin/referencecriterion/upload", method = RequestMethod.GET)
    public ModelAndView uploadReferenceCriterion(){
        ModelAndView model = new ModelAndView("/admin/referencecriterion/upload");
        return model;
    }

    @RequestMapping(value = "/admin/referencecriterion/upload", method = RequestMethod.POST)
    public String post(@RequestParam MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            File tempFile = File.createTempFile("oasis-import", ".tmp");
            FileUtils.writeByteArrayToFile(tempFile, file.getBytes());
            try {
                XmlImport xml = new XmlImport(tempFile);
                List<CriterionGroup> criterionGroups = xml.getCriterionGroups();
                criterionGroupService.save(criterionGroups);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/admin/referencecriterion/upload";
    }
}
