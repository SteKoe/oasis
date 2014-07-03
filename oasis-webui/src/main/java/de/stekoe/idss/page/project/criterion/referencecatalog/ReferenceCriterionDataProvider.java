package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.data.domain.PageRequest;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.page.PaginationConfigurator;
import de.stekoe.idss.service.ReferenceCriterionService;

public class ReferenceCriterionDataProvider implements IDataProvider<Criterion> {
    private static final Logger LOG = Logger.getLogger(ReferenceCriterionDataProvider.class);

    @Inject
    ReferenceCriterionService referenceCriterionService;

    @Inject
    PaginationConfigurator paginationConfigurator;

    @Override
    public Iterator<? extends Criterion> iterator(long first, long count) {

        int pageSize = paginationConfigurator.getValueFor(ReferenceCriterionListPage.class);

        int pageNum = 0;
        if(first != 0) {
            pageNum = (int) (first/pageSize - 1);
        }

        return referenceCriterionService.findAll(new PageRequest(pageNum, pageSize)).iterator();
    }

    @Override
    public long size() {
        return referenceCriterionService.count();
    }

    @Override
    public IModel<Criterion> model(final Criterion criterion) {
        return new Model(criterion);
    }

    @Override
    public void detach() {
        // NOP
    }
}
