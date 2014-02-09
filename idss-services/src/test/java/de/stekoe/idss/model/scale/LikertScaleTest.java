package de.stekoe.idss.model.scale;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.dao.IScaleDAO;
import de.stekoe.idss.model.ScaleFactory;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class LikertScaleTest extends AbstractBaseTest {
    @Inject
    IScaleDAO scaleDAO;

    @Test
    public void testEvenLikertScale() throws Exception {
        final OrdinalScale likertScale = ScaleFactory.createEvenLikertScale();
        likertScale.setName("Standard Likert Scale");
        likertScale.setDescription("The Likert Scale is a standard scale which aims to ...");
        scaleDAO.save(likertScale);
    }
}
