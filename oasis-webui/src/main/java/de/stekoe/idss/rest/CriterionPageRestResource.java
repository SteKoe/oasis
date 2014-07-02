package de.stekoe.idss.rest;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.PathParam;
import org.wicketstuff.rest.resource.gson.GsonRestResource;
import org.wicketstuff.rest.utils.http.HttpMethod;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.service.CriterionPageService;

public class CriterionPageRestResource extends GsonRestResource {
    private static final Logger LOG = Logger.getLogger(CriterionPageRestResource.class);

    @Inject
    private CriterionPageService criterionPageService;

    @MethodMapping(value = "/move/{direction}/{pageId}/{delta}", httpMethod = HttpMethod.GET)
    public void move(@PathParam("direction") String direction, @PathParam("pageId") String pageId, @PathParam("delta") int delta) {
        CriterionPage criterionPage = criterionPageService.findOne(pageId);
        if("up".equalsIgnoreCase(direction)) {
            criterionPageService.moveUp(criterionPage, delta);
        } else if("down".equalsIgnoreCase(direction)) {
            criterionPageService.moveDown(criterionPage, delta);
        }
    }

    @MethodMapping("test")
    public String test() {
        return "ABC";
    }
}
