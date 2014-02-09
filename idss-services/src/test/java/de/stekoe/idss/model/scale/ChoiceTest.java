package de.stekoe.idss.model.scale;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.dao.IChoiceDAO;
import de.stekoe.idss.dao.IScaleDAO;
import de.stekoe.idss.model.ScaleFactory;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ChoiceTest extends AbstractBaseTest {
    @Inject
    IScaleDAO scaleDAO;

    @Inject
    IChoiceDAO choiceDAO;

    @Test
    public void test() throws Exception {
        final OrdinalScale evenLikertScale = ScaleFactory.createEvenLikertScale();
        scaleDAO.save(evenLikertScale);

        final Choice choice = new Choice();
        choice.setScale(evenLikertScale);
        choice.setMeasurementValue(evenLikertScale.getValues().get(1));

        choiceDAO.save(choice);
    }
}
