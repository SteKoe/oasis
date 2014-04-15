package de.stekoe.idss.wicket;

import org.apache.wicket.Application;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.model.PermissionType;

public class EnumChoiceRendererTest extends AbstractWicketApplicationTester {

    private final EnumChoiceRenderer<PermissionType> dayEnumChoiceRenderer = new EnumChoiceRenderer<PermissionType>();

    @Test
    @DirtiesContext
    public void displayValueIsTranslated() throws Exception {
        final Object displayValue = dayEnumChoiceRenderer.getDisplayValue(PermissionType.CREATE);
        Assert.assertEquals(getString(PermissionType.CREATE.getKey()), displayValue.toString());
    }

    @Test
    @DirtiesContext
    public void idValueIsNameOfEnum() throws Exception {
        final String idValue = dayEnumChoiceRenderer.getIdValue(PermissionType.CREATE, 0);
        Assert.assertEquals("CREATE", idValue);
    }

    private String getString(String key) {
        return Application.get().getResourceSettings().getLocalizer().getString(key, null);
    }
}
