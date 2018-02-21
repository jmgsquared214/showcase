/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright (c) 2011 Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.client.messaging;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.Messaging;
import com.smartgwt.client.rpc.MessagingCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class StockChartSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "<p>The chart is receiving simulated, real-time updates of stock data via the "+
        "Real Time Messaging (RTM) module. Updates stop after 90 seconds but can be "+
        "restarted with the &ldquo;Generate more updates&rdquo; button. "+
        "Right-click on the chart to switch the type of visualization.</p> " +
        "<p>The RTM module provides low-latency, high data volume streaming capabilities for "+
        "latency-sensitive applications such as trading desks and operations centers. "+
        "It can connect to Java Message Service (JMS) channels without "+
        "writing any code, or can be connected to custom messaging solutions with a simple "+
        "adapter.</p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public HTMLFlow getDisabledViewPanel() {
            String modules = "";
            if (!SC.hasAnalytics()) {
                modules += "<a href=\"http://www.smartclient.com/product/index.jsp\" " + 
                    "target=\"_blank\">Analytics module</a>";
            }
            if (!SC.hasRealtimeMessaging()) {
                if (modules.length() > 0) modules += " and ";
                modules += "<a href=\"http://www.smartclient.com/product/index.jsp\" " +
                    "target=\"_blank\">Real Time Messaging module</a>";
            }
            final HTMLFlow htmlFlow = new HTMLFlow("<div class='explorerCheckErrorMessage'>" +
                "<p>This example is disabled because it requires the optional " + modules +
                ".</p><p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/" +
                "#messaging_stock_chart\" target=\"\">here</a> to see this example " +
                                                   "on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return SC.hasRealtimeMessaging() && SC.hasAnalytics();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new StockChartSample();
        }
    }
    
    @Override
    protected boolean isTopIntro() {
    	return true;
    }

    RecordList initialData;
    RecordList lastValues;
    RecordList chartData;
    FacetChart chart;

    public Canvas getViewPanel() {
        final long startParameter = System.currentTimeMillis();


        VLayout contentLayout = new VLayout();
        contentLayout.setMembersMargin(10);
        contentLayout.setPadding(10);

        final Button generateUpdatesButton = new Button("Generate more updates");
        generateUpdatesButton.setWidth(200);
        generateUpdatesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                generateUpdates(startParameter, generateUpdatesButton);
            }
        });
        contentLayout.addMember(generateUpdatesButton);

        chart = new FacetChart();
        chart.setFacets(new Facet("lastUpdated", "lastUpdated"), new Facet("name", "Name"));
        chart.setValueProperty("lastValue");
        chart.setChartType(ChartType.AREA);
        chart.setTitle("Portfolio Value");
        chart.setMinHeight(400);
        contentLayout.addMember(chart);

        // receive messages from the stockQuotes channel and update data grid
        Messaging.subscribe("stockQuotes" + startParameter, new MessagingCallback() {
            @Override
            public void execute(Object data) {
                updateStockChart(data);
            }
        });

        StockQuotesDS.getInstance().fetchData(null, new DSCallback() {
            public void execute(DSResponse response, Object data, DSRequest request) {
                initialData = new RecordList(response.getData());
                lastValues = new RecordList(initialData.duplicate());
                chartData = new RecordList(initialData.duplicate());
                String currentDate = dateFormatter.format(new Date());
                for (int i=0; i<chartData.getLength(); i++) {
                    Record r = chartData.get(i);
                    r.setAttribute("lastUpdated", currentDate);
                }
            }
        });

        generateUpdates(startParameter, generateUpdatesButton);

        return contentLayout;
    }

    final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm:ss.S");
    @SuppressWarnings("unchecked")
    private void updateStockChart(Object data) {
        List<List<?>> stockData = (List<List<?>>) JSOHelper.convertToJava((JavaScriptObject) data);
        String currentDate = dateFormatter.format(new Date());
        RecordList newChartData =  new RecordList(chartData.duplicate());
        for (List<?> recordData : stockData) {
            float change = ((Number) recordData.get(1)).floatValue();
            Integer id = (Integer) recordData.get(0);
            Record record = initialData.find("id",id);
            String name = record.getAttributeAsString("name");
            int lastValueRecordIndex = lastValues.findIndex("name",name);
            Record lastValueRecord = lastValues.get(lastValueRecordIndex);
            float lastValue = lastValueRecord.getAttributeAsFloat("lastValue").floatValue();
            lastValue *= 1.0 + 20.0*0.01*change;
            Record newRecord = new Record();
            newRecord.setAttribute("lastValue", lastValue);
            newRecord.setAttribute("lastUpdated", currentDate);
            newRecord.setAttribute("name", name);
            lastValues.set(lastValueRecordIndex,newRecord);
        }
        newChartData.addList(lastValues.toArray());
        int recordsToRemove = newChartData.getLength() - 20*lastValues.getLength();
        if (recordsToRemove>0) {
        	newChartData.removeList(newChartData.getRange(0,recordsToRemove));
        }
        chart.setData(newChartData);
        chartData = newChartData;
    }

    private void generateUpdates(final long startParameter, final Button generateUpdatesButton) {
        generateUpdatesButton.disable();
        RPCRequest request = new RPCRequest();
        // we tells servlet which channel it should use for sending data
        request.setActionURL("examples/StockQuotes/generate?sp=" + startParameter);
        RPCManager.sendRequest(request);
        // block button repeat click for 90 seconds - time while servlet
        // will sends data to us
        new Timer() {
            public void run() {
                generateUpdatesButton.enable();
            }
        }.schedule(90000);
    }
    
    public String getIntro() {
        return DESCRIPTION;
    }
    
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true),
            new SourceEntity("server/StockQuotesServlet.java", JAVA, "source/messaging/StockQuotesServlet.java.html", true),
            new SourceEntity("StockQuotesDS.java", JAVA, "source/messaging/StockQuotesDS.java.html", false),
            new SourceEntity("stockQuotes.data.xml", XML, "source/test_data/stockQuotes.data.xml.html", true)
        };
    }
}