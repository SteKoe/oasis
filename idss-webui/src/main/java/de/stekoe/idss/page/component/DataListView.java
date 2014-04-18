package de.stekoe.idss.page.component;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import de.stekoe.idss.model.NamedElement;

public abstract class DataListView<T extends NamedElement> extends Panel {

    public static final String BUTTON_ID = "button";

    private IDataProvider<T> dataProvider;
    private long itemsPerPage;
    private DataView<T> dataView;

    private final boolean showDeleteButton = true;
    private final boolean showDetailsButton = true;

    public DataListView(String wicketId, IDataProvider<T> dataProvider) {
        this(wicketId, dataProvider, 10);
    }

    public DataListView(String wicketId, IDataProvider<T> dataProvider, long itemsPerPage) {
        super(wicketId);
        this.dataProvider = dataProvider;
        this.itemsPerPage = itemsPerPage;

        dataView = new DataView<T>("items", dataProvider, itemsPerPage) {
            @Override
            protected void populateItem(Item<T> item) {
                T modelObject = item.getModelObject();

                item.add(new Label("name", modelObject.getName()));
                item.add(new ListView<Link>("buttons", getButtons(modelObject)) {
                    @Override
                    protected void populateItem(ListItem<Link> item) {
                        item.add(item.getModelObject());
                    }
                });
            }
        };
        add(dataView);

        add(new Label("noItemLabel", getString("table.empty")) {
            @Override
            public boolean isVisible() {
                return dataView.getItemCount() <= 0;
            }
        });

        add(new BootstrapPagingNavigator("navigator", dataView));
    }

    protected abstract List<? extends Link> getButtons(final T modelObject);
}
