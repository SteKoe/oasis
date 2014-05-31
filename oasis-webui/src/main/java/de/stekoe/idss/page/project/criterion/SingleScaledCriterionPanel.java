package de.stekoe.idss.page.project.criterion;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;
import de.stekoe.idss.session.WebSession;

public class SingleScaledCriterionPanel<T extends SingleScaledCriterion<? extends MeasurementValue>> extends PageElementPanel<T> {

    @Inject
    private UserChoiceRepository userChoiceRepository;

    private final IModel<UserChoice> model;

    public SingleScaledCriterionPanel(String id, T pageElement) {
        super(id, pageElement);
        model = new UserChoiceModel(getPageElement());
    }

    @Override
    public IModel<UserChoice> getModel() {
        return this.model;
    }

    class UserChoiceModel extends Model<UserChoice>{
        private final String id;
        private UserChoice choice;
        private final T criterion;

        public UserChoiceModel(T criterion) {
            this.criterion = criterion;
            this.id = criterion.getId();
        }

        @Override
        public void detach() {
            choice = null;
        }

        @Override
        public UserChoice getObject() {
            if(choice != null) {
                return choice;
            }
            if(this.id != null) {
                choice = userChoiceRepository.findByUserAndCriterion(WebSession.get().getUser().getId(), id);
            }
            if(choice == null) {
                choice = new UserChoice();
                choice.setCriterion(this.criterion);
            }

            return choice;
        }
    }
}
