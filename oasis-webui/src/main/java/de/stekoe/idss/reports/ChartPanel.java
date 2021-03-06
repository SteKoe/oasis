package de.stekoe.idss.reports;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.stekoe.amcharts.AmChart;
import de.stekoe.amcharts.addition.Color;
import de.stekoe.amcharts.addition.ColorSerialiser;
import de.stekoe.idss.OASISWebApplication;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.SingleScaledCriterion;

public abstract class ChartPanel extends Panel {
    private final String divID = "chart"+DigestUtils.md5Hex(UUID.randomUUID().toString());

    public ChartPanel(String wicketId, IModel<Criterion> model) {
        super(wicketId, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        WebComponent webComponent = new WebComponent("chartdiv");
        webComponent.add(new AttributeModifier("id", getDivId()));

        Criterion criterion = (Criterion) getDefaultModel().getObject();
        if(criterion instanceof SingleScaledCriterion) {
            int numOfValues = ((SingleScaledCriterion) criterion).getValues().size();
            webComponent.add(new AttributeModifier("style", "height: " + ((numOfValues * 30) + 150) + "px"));
        }

        add(webComponent);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Color.class, new ColorSerialiser());
        if(RuntimeConfigurationType.DEVELOPMENT.equals(OASISWebApplication.get().getConfigurationType())) {
            gsonBuilder.setPrettyPrinting();
        }
        gson = gsonBuilder.create();
        String string = String.format("var %1$s = AmCharts.makeChart(\"%1$s\", %2$s);", getDivId(), gson.toJson(getChart()).toString());
        response.render(JavaScriptHeaderItem.forScript(string, null));
    }

    private String getDivId() {
        return divID;
    }

    protected abstract AmChart getChart();
}
