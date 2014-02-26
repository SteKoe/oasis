package de.stekoe.idss.page.component.criterion;

import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class OrdinalScalePanel extends Panel {

    private final String criterion;
    private final OrdinalScale scale;
    private boolean horizontal = false;

    public OrdinalScalePanel(String id, SingleScaledCriterion ssc) {
        this(id, ssc.getScale().getName(), (OrdinalScale) ssc.getScale());
    }

    public OrdinalScalePanel(String id, String criterion, OrdinalScale scale) {
        super(id);

        this.criterion = criterion;
        this.scale = scale;

        final Label criterionLabel = new Label("criterion", criterion) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);

                if (isHorizontal()) {
                    final String css = tag.getAttribute("class");
                    tag.put("class", css + " col-md-5");
                }
            }
        };
        add(criterionLabel);

        final RadioGroup radioGroup = new RadioGroup("radio.group") {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);

                if (isHorizontal()) {
                    final String css = tag.getAttribute("class");
                    tag.put("class", css + " col-md-7");
                }
            }
        };
        radioGroup.setRenderBodyOnly(false);
        add(radioGroup);

        radioGroup.add(new ListView<OrdinalValue>("measurement.values", scale.getValues()) {
            @Override
            protected void populateItem(ListItem<OrdinalValue> item) {
                final OrdinalValue ordinalValue = item.getModelObject();

                item.add(new Label("label", ordinalValue.getValue()));
                item.add(new Radio("radio", Model.of(ordinalValue.getId())));
            }
        });
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public boolean isHorizontal() {
        return horizontal;
    }
}
