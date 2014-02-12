package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.component.criterion.MultiScaledCriterionPanel;
import de.stekoe.idss.model.criterion.MultiScaledCriterion;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.scale.OrdinalScale;
import de.stekoe.idss.model.scale.OrdinalValue;
import de.stekoe.idss.page.LayoutPage;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Arrays;


/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class FormTestPage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(FormTestPage.class);

    public FormTestPage(PageParameters pageParameters) {
        super(pageParameters);

        final SingleScaledCriterion ssc1 = getFirstCriterion();
        final SingleScaledCriterion ssc2 = getSecondCriterion();

        final MultiScaledCriterion multiScaledCriterion = new MultiScaledCriterion();
        multiScaledCriterion.setName("Patientenverfügung");
        multiScaledCriterion.setDescription("Die Patientenverfügung hat die Aufgabe, dem WIllen der Patienten Ausdruck zu und Geltung zu verschaffen, wenn er selbst dazu nicht meh in der Lage ist. \"Ein 55-jähriger Mann erleidet einen schweren Autounfall und liegt seidem im Koma. Der Patient wurde als Notfall eingeliefert und wird derzeit künstlich beatmet. Außerdem wird darüber nachgedacht, den atienten über eine Magensonde (PEG) künstlich zu ernähren. In der Patientenverfügung wünscht der Patient für diesen Fall keine lebensverlängernden Maßnahmen.\" Wie beurteilen Sie folgende Aussagen?");
        multiScaledCriterion.getSubCriterions().add(ssc1);
        multiScaledCriterion.getSubCriterions().add(ssc2);

        final Panel components = new MultiScaledCriterionPanel("panel.1", multiScaledCriterion);
        add(components);
    }

    private SingleScaledCriterion getFirstCriterion() {
        final OrdinalScale ssc1scale = new OrdinalScale();
        ssc1scale.setName("A) Der Patient sollte mit einer PEG-Magensonde versorgt werden.");
        ssc1scale.setValues(Arrays.asList(
                new OrdinalValue(1, "stimme voll zu"),
                new OrdinalValue(2, "stimme zu"),
                new OrdinalValue(3, "neutral"),
                new OrdinalValue(4, "stimme nicht zu"),
                new OrdinalValue(5, "stimme gar nicht zu")
        ));
        final SingleScaledCriterion ssc1 = new SingleScaledCriterion();
        ssc1.setScale(ssc1scale);
        return ssc1;
    }

    private SingleScaledCriterion getSecondCriterion() {
        final OrdinalScale scale = new OrdinalScale();
        scale.setName("B) In diesem Fall sollte die Beatmung des Patienten abgebrochen werden.");
        scale.setValues(Arrays.asList(
                new OrdinalValue(1, "stimme voll zu"),
                new OrdinalValue(2, "stimme zu"),
                new OrdinalValue(3, "neutral"),
                new OrdinalValue(4, "stimme nicht zu"),
                new OrdinalValue(5, "stimme gar nicht zu")
        ));
        final SingleScaledCriterion ssc1 = new SingleScaledCriterion();
        ssc1.setScale(scale);
        return ssc1;

    }
}
