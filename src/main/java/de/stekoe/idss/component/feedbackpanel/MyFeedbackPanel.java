package de.stekoe.idss.component.feedbackpanel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.settings.IApplicationSettings;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert;

@SuppressWarnings("serial")
public class MyFeedbackPanel extends Panel implements IFeedback {

	private static final Logger LOG = Log.getLogger(MyFeedbackPanel.class);

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

	public MyFeedbackPanel(final String id) {
		this(id, null);
	}

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

	public final boolean anyErrorMessage() {
		return anyMessage(FeedbackMessage.ERROR);
	}

	public final boolean anyMessage() {
		return anyMessage(FeedbackMessage.UNDEFINED);
	}

	public final boolean anyMessage(int level) {
		List<FeedbackMessage> msgs = getCurrentMessages();

		for (FeedbackMessage msg : msgs) {
			if (msg.isLevel(level)) {
				return true;
			}
		}

		return false;
	}

	public final FeedbackMessagesModel getFeedbackMessagesModel() {
		return (FeedbackMessagesModel) messageListView.getDefaultModel();
	}

	public final IFeedbackMessageFilter getFilter() {
		return getFeedbackMessagesModel().getFilter();
	}

	public final Comparator<FeedbackMessage> getSortingComparator() {
		return getFeedbackMessagesModel().getSortingComparator();
	}

	@Override
	public boolean isVersioned() {
		return false;
	}

	public final MyFeedbackPanel setFilter(IFeedbackMessageFilter filter) {
		getFeedbackMessagesModel().setFilter(filter);
		return this;
	}

	public final MyFeedbackPanel setMaxMessages(int maxMessages) {
		messageListView.setViewSize(maxMessages);
		return this;
	}

	public final MyFeedbackPanel setSortingComparator(
			Comparator<FeedbackMessage> sortingComparator) {
		getFeedbackMessagesModel().setSortingComparator(sortingComparator);
		return this;
	}

	protected String getCSSClass(final FeedbackMessage message) {
		return "feedbackPanel" + message.getLevelAsString();
	}

	protected final List<FeedbackMessage> getCurrentMessages() {
		final List<FeedbackMessage> messages = messageListView.getModelObject();
		return Collections.unmodifiableList(messages);
	}

	protected FeedbackMessagesModel newFeedbackMessagesModel() {
		return new FeedbackMessagesModel(this);
	}

	protected Component newMessageDisplayComponent(String id, FeedbackMessage message) {
		Serializable serializable = message.getMessage();
		Label label = new Label(id, (serializable == null) ? "" : serializable.toString());
		label.setEscapeModelStrings(MyFeedbackPanel.this.getEscapeModelStrings());
		
		Alert alert = new Alert(id, Model.of(serializable.toString()));
		int level = message.getLevel();
		
		switch(level) {
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
