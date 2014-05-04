package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.stekoe.idss.amcharts.GraphValue;
import de.stekoe.idss.amcharts.SerialGraph;
import de.stekoe.idss.model.Criterion;

public class OccurrencesChartPanel extends Panel {
    private final SerialGraph serialGraph;

    public OccurrencesChartPanel(String wicketId, IModel<Criterion> model) {
        super(wicketId, model);

        serialGraph = new SerialGraph("chartdiv");
        serialGraph.setCategoryField("country");

        GraphValue graphValue = new GraphValue();
        graphValue.put("country", "USA");
        graphValue.put("visits", 2025);
        serialGraph.getGraphValues().add(graphValue);

        graphValue = new GraphValue();
        graphValue.put("country", "China");
        graphValue.put("visits", 1882);
        serialGraph.getGraphValues().add(graphValue);

        WebComponent webComponent = new WebComponent("chartdiv");
        webComponent.add(new AttributeModifier("id", serialGraph.getHtmlId()));
        add(webComponent);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forScript(serialGraph.toJson(), serialGraph.getHtmlId() + "js"));
    }
}
