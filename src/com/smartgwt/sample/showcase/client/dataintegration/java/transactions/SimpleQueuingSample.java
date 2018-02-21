/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
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

package com.smartgwt.sample.showcase.client.dataintegration.java.transactions;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.QueueSentCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class SimpleQueuingSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>Queuing allows any set of DataSource operations to be combined into a single HTTP request, without requiring" +
            " any special code to be written to transport the combined inputs and outputs.</p>" +
            "<p>Click the \"Find Orders\" button and the example will load both the selected user's details and all the " +
            "orders associated with that user, as a single request. Queuing works transparently to the components involved," +
            " so for example, scrolling down in the orders grid causes data paging to be activated, exactly as though the grid" +
            " had done a fetch that was not combined into a queue.</p>" +
            "<p>Since queuing is transparent to components, a screen full of various components which need to load data from" +
            " different sources can participate in a queue without any special component-specific code, and with no need to" +
            " rework how data is transferred if new components are added. Each component can be treated as though it were" +
            " standalone.</p>" +
            "<p>Server-side, queuing supports focusing on simple, secure, reusable data operations and other services," +
            " which can then be accessed in arbitrary combinations according to the data loading and saving requirements of" +
            " particular screens, with no need to write brittle, screen-specific server code.</p>" +
            "<p>Queuing works even when the operations are on different data providers (as in this case, where the user " +
            "details are coming from Hibernate and the order details are coming from the Smart GWT Server SQL provider).</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            SimpleQueuingSample panel = new SimpleQueuingSample();
            id = panel.getID();
            return panel;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {

        DataSource queuingUserDS = DataSource.get("queuing_userHB");
        DataSource queuingOrderDS = DataSource.get("queuing_order");

        final SectionStack sectionStack = new SectionStack();
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setWidth(500);
        sectionStack.setHeight(400);

        SectionStackSection userDetailsSection = new SectionStackSection("User Details");
        userDetailsSection.setExpanded(true);

        final DetailViewer userDetailViewer = new DetailViewer();
        userDetailViewer.setDataSource(queuingUserDS);
        userDetailViewer.setUseAllDataSourceFields(true);

        userDetailsSection.setItems(userDetailViewer);

        SectionStackSection ordersSection = new SectionStackSection("Orders");
        ordersSection.setExpanded(true);

        final ListGrid ordersListGrid = new ListGrid();
        ordersListGrid.setDataSource(queuingOrderDS);
        ordersListGrid.setDrawAheadRatio(1.0f);
        ordersListGrid.setShowFilterEditor(true);

        ordersListGrid.setFields(new ListGridField("orderID","Order ID",100), new ListGridField("customerName"),
                new ListGridField("orderDate","Order Date",120), new ListGridField("totalValue","Total Value",100));

        //deliberately small to force paging
        ordersListGrid.setDataPageSize(10);

        //disable this performance feature, again to force paging
        ordersListGrid.setDrawAllMaxCells(0);

        ordersSection.setItems(ordersListGrid);

        sectionStack.setSections(userDetailsSection);
        sectionStack.setSections(ordersSection);
        sectionStack.setVisible(false);


        //main form
        final DynamicForm findOrdersForm = new DynamicForm();
        findOrdersForm.setWidth(400);

        TextItem userItem = new TextItem("userID", "User ID");
        userItem.setDefaultValue(1);
        userItem.setWidth(50);
        userItem.setWrapHintText(false);
        userItem.setHint("Note:&nbsp;sample&nbsp;data&nbsp;only exists for user ID 1");

        ButtonItem findButtonItem = new ButtonItem("findButton", "Find Orders");
        findButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                sectionStack.setVisible(true);

                RPCManager.startQueue();
                userDetailViewer.fetchData(findOrdersForm.getValuesAsCriteria());
                ordersListGrid.fetchData(findOrdersForm.getValuesAsCriteria());
                RPCManager.sendQueue();
            }
        });

        findOrdersForm.setItems(userItem, findButtonItem);

        VLayout layout = new VLayout(15);
        layout.addMember(findOrdersForm);
        layout.addMember(sectionStack);

        final ServerCountLabel serverCountLabel = new ServerCountLabel();
        serverCountLabel.setLeft(520);
        serverCountLabel.setTop(100);

        layout.addChild(serverCountLabel);

        RPCManager.setQueueSentCallback(new QueueSentCallback() {
            public void queueSent(RPCRequest[] requests) {
                serverCountLabel.incrementAndUpdate(requests);
                //flash the label
                serverCountLabel.setBackgroundColor("ffff77");
                new Timer() {
                    public void run() {
                        serverCountLabel.setBackgroundColor("ffffff");
                    }
                }.schedule(500);
            }
        });

        return layout;

    }

    class ServerCountLabel extends Label {
        private int count = 0;

        ServerCountLabel() {
            setPadding(10);
            setWidth(300);
            setHeight(40);
            setBorder("1px solid grey");
            setContents("<b>Number of server trips: 0<br>No queues sent</b>");
        }

        public void incrementAndUpdate(RPCRequest[] requests) {
            count++;
            setContents("<b>Number of server trips: " + count +
                    "<br/>Last queue contained: " + requests.length + " request(s)</b>");
        } 
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true),
                new SourceEntity("server/User.java", JAVA, "source/beans/User.java.html", true)
        };
    }
}