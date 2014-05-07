package de.stekoe.idss.page.project.criterion;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;
import de.stekoe.idss.session.WebSession;

public abstract class OrdinalScaledCriterionPanel extends PageElementPanel<OrdinalScaledCriterion> {

    @Inject
    UserChoiceRepository userChoiceRepository;
    private final IModel<UserChoice> model;

    public OrdinalScaledCriterionPanel(String wicketId, OrdinalScaledCriterion criterion) {
        super(wicketId, criterion);
        model = new UserChoiceModel(getPageElement().getId());
        renderChoices();
    }

    abstract void renderChoices();

    @Override
    public IModel<UserChoice> getModel() {
        return this.model;
    }

    class UserChoiceModel extends Model<UserChoice>{
        private final String id;
        private UserChoice choice;

        public UserChoiceModel(String id) {
            this.id = id;
        }

        @Override
        public void detach() {
            this.choice = null;
        }

        @Override
        public UserChoice getObject() {
            if(this.choice != null) {
                return this.choice;
            }
            if(this.id != null) {
                this.choice = userChoiceRepository.findByUserAndCriterion(WebSession.get().getUser().getId(), id);
            }
            if(this.choice == null) {
                this.choice = new UserChoice();
            }
            return this.choice;
        }
    }
}
