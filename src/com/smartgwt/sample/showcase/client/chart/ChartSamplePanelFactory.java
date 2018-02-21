package com.smartgwt.sample.showcase.client.chart;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;

public abstract class ChartSamplePanelFactory extends AdvancedPanelFactory {

    @Override
    public final Canvas getDisabledViewPanel() {
        final HTMLFlow htmlFlow = new HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the " +
                "<a href=\"http://smartclient.com/product/#html5Charts\" target=\"_blank\">HTML5 Charts</a> feature of Smart GWT Pro or better.</p>.</p>" +
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#" + getNodeID() + "\" target=\"_blank\">here</a> to see this example on SmartClient.com.</p></div>");
        htmlFlow.setWidth100();
        return htmlFlow;
    }

    @Override
    public boolean isEnabled() {
        return (SC.hasDrawing() && SC.hasCharts());
    }

    public abstract String getNodeID();
}
