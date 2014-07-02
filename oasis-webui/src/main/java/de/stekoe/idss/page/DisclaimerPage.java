package de.stekoe.idss.page;

import org.apache.wicket.model.ResourceModel;

public class DisclaimerPage extends ContainerLayoutPage {
    public DisclaimerPage() {
        setTitle(new ResourceModel("label.disclaimer").getObject());
    }
}
