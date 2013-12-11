package de.stekoe.idss.component.feedbackpanel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class MyFeedbackPanel extends Panel implements IFeedback {

    private static final Logger LOG = Logger.getLogger(MyFeedbackPanel.class);

    private final class MessageListView extends ListView<FeedbackMessage> {
        private static final long serialVersionUID = 1L;

        public MessageListView(final String id) {
            super(id);
            setDefaultModel(newFeedbackMessagesModel());
        }

        @Override
        protected IModel<FeedbackMessage> getListItemModel(
                final IModel<? extends List<FeedbackMessage>> listViewModel,
                final int index) {
            return new AbstractReadOnlyModel<FeedbackMessage>() {
                private static final long serialVersionUID = 1L;

                @Override
                public FeedbackMessage getObject() {
                    if (index >= listViewModel.getObject().size()) {
                        return null;
                    } else {
                        return listViewModel.getObject().get(index);
                    }
                }
            };
        }

        @Override
        protected void populateItem(final ListItem<FeedbackMessage> listItem) {
            final FeedbackMessage message = listItem.getModelObject();
            message.markRendered();
            final Component label = newMessageDisplayComponent("message",
                    message);
            final AttributeModifier levelModifier = AttributeModifier.append(
                    "class", getCSSClass(message));
            label.add(levelModifier);
            listItem.add(levelModifier);
            listItem.add(label);
        }
    }

    private static final long serialVersionUID = 1L;

    private final MessageListView messageListView;

    /**
     * @param id The id of this component
     */
    public MyFeedbackPanel(final String id) {
        this(id, null);
    }

    /**
     * @param id The id of this component.
     * @param filter Initial {@code IFeedbackMessageFilter}.
     */
    public MyFeedbackPanel(final String id, IFeedbackMessageFilter filter) {
        super(id);
        WebMarkupContainer messagesContainer = new WebMarkupContainer(
                "feedbackul") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(anyMessage());
            }
        };
        add(messagesContainer);
        messageListView = new MessageListView("messages");
        messageListView.setVersioned(false);
        messagesContainer.add(messageListView);

        if (filter != null) {
            setFilter(filter);
        }
    }

    /**
     * @return Checks wether there are error messages.
     */
    public final boolean anyErrorMessage() {
        return anyMessage(FeedbackMessage.ERROR);
    }

    /**
     * @return Checks wether there are any messages.
     */
    public final boolean anyMessage() {
        return anyMessage(FeedbackMessage.UNDEFINED);
    }

    /**
     * @param level Message level defined via {@link FeedbackMessage} constants.
     * @return Checks wether there are any messages for the fiven message level.
     */
    public final boolean anyMessage(int level) {
        List<FeedbackMessage> msgs = getCurrentMessages();

        for (FeedbackMessage msg : msgs) {
            if (msg.isLevel(level)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return The {@code FeedbackMessagesModel} used.
     */
    public final FeedbackMessagesModel getFeedbackMessagesModel() {
        return (FeedbackMessagesModel) messageListView.getDefaultModel();
    }

    /**
     * @return The {@code IFeedbackMessageFilter} used.
     */
    public final IFeedbackMessageFilter getFilter() {
        return getFeedbackMessagesModel().getFilter();
    }

    /**
     * @return The {@code Comparator} which is used to sort the {@code FeedbackMessage}s.
     */
    public final Comparator<FeedbackMessage> getSortingComparator() {
        return getFeedbackMessagesModel().getSortingComparator();
    }

    @Override
    public boolean isVersioned() {
        return false;
    }

    /**
     * @param filter The {@code IFeedbackMessageFilter} which is applied to the {@code FeedbackMessagesModel}.
     * @return {@code this} for chaining purpose.
     */
    public final MyFeedbackPanel setFilter(IFeedbackMessageFilter filter) {
        getFeedbackMessagesModel().setFilter(filter);
        return this;
    }

    /**
     * @param maxMessages The maximum amount of messages to show.
     * @return {@code this} for chaining purpose.
     */
    public final MyFeedbackPanel setMaxMessages(int maxMessages) {
        messageListView.setViewSize(maxMessages);
        return this;
    }

    /**
     * @param sortingComparator The {@code Comparator} which should be used to sort the {@code FeedbackMessage}s.
     * @return {@code this} for chaining purpose.
     */
    public final MyFeedbackPanel setSortingComparator(
            Comparator<FeedbackMessage> sortingComparator) {
        getFeedbackMessagesModel().setSortingComparator(sortingComparator);
        return this;
    }

    private String getCSSClass(final FeedbackMessage message) {
        return "feedbackPanel" + message.getLevelAsString();
    }

    private final List<FeedbackMessage> getCurrentMessages() {
        List<FeedbackMessage> messages = messageListView.getModelObject();
        return Collections.unmodifiableList(messages);
    }

    /**
     * @return A new instance of the {@link FeedbackMessagesModel}.
     */
    protected FeedbackMessagesModel newFeedbackMessagesModel() {
        return new FeedbackMessagesModel(this);
    }

    private Component newMessageDisplayComponent(String id, FeedbackMessage message) {
        if (message == null) {
            return null;
        }

        Serializable serializable = message.getMessage();
        Label label = new Label(id, (serializable == null) ? "" : serializable.toString());
        label.setEscapeModelStrings(MyFeedbackPanel.this.getEscapeModelStrings());

        if (serializable == null) {
            return null;
        }

        Alert alert = new Alert(id, Model.of(serializable.toString()));
        int level = message.getLevel();

        switch (level) {
        case FeedbackMessage.ERROR:
        case FeedbackMessage.FATAL:
            alert.type(Alert.Type.Error);
            break;
        case FeedbackMessage.SUCCESS:
            alert.type(Alert.Type.Success);
            break;
        case FeedbackMessage.WARNING:
            alert.type(Alert.Type.Warning);
            break;
        case FeedbackMessage.INFO:
        default:
            alert.type(Alert.Type.Info);
        }
        return alert;
    }
}
