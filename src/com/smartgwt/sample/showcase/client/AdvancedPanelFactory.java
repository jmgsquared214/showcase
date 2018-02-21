package com.smartgwt.sample.showcase.client;

import com.smartgwt.client.widgets.Canvas;

public abstract class AdvancedPanelFactory implements PanelFactory {

    private static int nextTmpId = 1;

    private String id;

    protected AdvancedPanelFactory() {
        id = "tmp" + Integer.toString(nextTmpId++);
    }

    public abstract Canvas getDisabledViewPanel();

    public abstract boolean isEnabled();

    @Override
    public final String getID() {
        return id;
    }

    @Override
    public final ShowcasePanel create() {
        final ShowcasePanel showcasePanel = createShowcasePanel();
        if (showcasePanel != null) id = showcasePanel.getID();
        return showcasePanel;
    }

    public abstract ShowcasePanel createShowcasePanel();
}
