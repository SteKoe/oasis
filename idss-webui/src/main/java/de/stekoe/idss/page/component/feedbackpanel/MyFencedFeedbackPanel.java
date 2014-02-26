package de.stekoe.idss.page.component.feedbackpanel;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.feedback.FeedbackCollector;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class MyFencedFeedbackPanel extends MyFeedbackPanel {
    private static final long serialVersionUID = 1L;

    private static final MetaDataKey<Integer> FENCE_KEY = new MetaDataKey<Integer>() {
        private static final long serialVersionUID = 1L;
    };

    private final Component fence;

    /**
     * @param id The id of this component
     */
    public MyFencedFeedbackPanel(String id) {
        this(id, (Component) null);
    }

    /**
     * @param id    The id of this component
     * @param fence The fencing component
     */
    public MyFencedFeedbackPanel(String id, Component fence) {
        this(id, fence, null);
    }

    /**
     * @param id     The id of this component
     * @param filter A filter applied on the feedback messages
     */
    public MyFencedFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        this(id, null, filter);
    }

    /**
     * @param id     The id of this component
     * @param fence  The fencing component
     * @param filter A filter applied on the feedback messages
     */
    public MyFencedFeedbackPanel(String id, Component fence,
                                 IFeedbackMessageFilter filter) {
        super(id, filter);
        this.fence = fence;
        if (fence != null) {
            Integer count = fence.getMetaData(FENCE_KEY);
            count = count == null ? 1 : count + 1;
            fence.setMetaData(FENCE_KEY, count);
        }
    }

    @Override
    protected void onRemove() {
        super.onRemove();
        if (fence != null) {
            // decrement the fence count

            Integer count = fence.getMetaData(FENCE_KEY);
            count = (count == null || count == 1) ? null : count - 1;
            fence.setMetaData(FENCE_KEY, count);
        }
    }

    @Override
    protected FeedbackMessagesModel newFeedbackMessagesModel() {
        return new FeedbackMessagesModel(this) {
            private static final long serialVersionUID = 1L;

            @Override
            protected List<FeedbackMessage> collectMessages(Component panel, IFeedbackMessageFilter filter) {
                if (fence == null) {
                    FeedbackCollector fc = new FeedbackCollector(panel.getPage()) {
                        @Override
                        protected boolean shouldRecurseInto(Component component) {
                            return component.getMetaData(FENCE_KEY) == null;
                        }
                    };
                    return fc.collect(filter);
                } else {
                    FeedbackCollector fc = new FeedbackCollector(fence) {
                        @Override
                        protected boolean shouldRecurseInto(Component component) {
                            return component.getMetaData(FENCE_KEY) == null;
                        }
                    };
                    fc.setIncludeSession(false);
                    return fc.collect(filter);
                }
            }
        };
    }
}
