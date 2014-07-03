package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.data.domain.PageRequest;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.PaginationConfigurator;
import de.stekoe.idss.service.ReferenceCriterionGroupService;

public class ReferenceCriterionGroupDataProvider implements IDataProvider<CriterionGroup> {
    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    @Inject
    PaginationConfigurator paginationConfigurator;

    @Override
    public Iterator<? extends CriterionGroup> iterator(long first, long count) {

        int pageSize = paginationConfigurator.getValueFor(ReferenceCriterionGroupListPage.class);

        int pageNum = 0;
        if(first != 0) {
            pageNum = (int) (first/pageSize - 1);
        }

        return referenceCriterionGroupService.findAll(new PageRequest(pageNum, pageSize)).iterator();
    }

    @Override
    public long size() {
        return referenceCriterionGroupService.count();
    }

    @Override
    public IModel<CriterionGroup> model(final CriterionGroup criterionGroup) {
        return new Model(criterionGroup);
    }

    @Override
    public void detach() {
        // NOP
    }
}
