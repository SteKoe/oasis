package de.stekoe.idss.component.feedbackpanel;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.feedback.FeedbackCollector;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

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
     * @see MyFencedFeedbackPanel#MyFencedFeedbackPanel(String, Component, IFeedbackMessageFilter)
     */
    public MyFencedFeedbackPanel(String id) {
        this(id, (Component) null);
    }

    /**
     * @see MyFencedFeedbackPanel#MyFencedFeedbackPanel(String, Component, IFeedbackMessageFilter)
     */
    public MyFencedFeedbackPanel(String id, Component fence) {
        this(id, fence, null);
    }

    /**
     * @see MyFencedFeedbackPanel#MyFencedFeedbackPanel(String, Component, IFeedbackMessageFilter)
     */
    public MyFencedFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        this(id, null, filter);
    }

    /**
     * @see MyFencedFeedbackPanel#MyFencedFeedbackPanel(String, Component, IFeedbackMessageFilter)
     */
    public MyFencedFeedbackPanel(String id, Component fence, IFeedbackMessageFilter filter) {
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
            protected List<FeedbackMessage> collectMessages(Component panel,
                    IFeedbackMessageFilter filter) {
                if (fence == null) {
                    // this is the catch-all panel

                    return new FeedbackCollector(panel.getPage()) {
                        @Override
                        protected boolean shouldRecurseInto(Component component) {
                            return component.getMetaData(FENCE_KEY) == null;
                        }
                    }.collect(filter);
                } else {
                    // this is a fenced panel

                    return new FeedbackCollector(fence) {
                        @Override
                        protected boolean shouldRecurseInto(Component component) {
                            // only recurse into components that are not fences

                            return component.getMetaData(FENCE_KEY) == null;
                        }
                    }.setIncludeSession(false).collect(filter);
                }
            }
        };
    }
}
