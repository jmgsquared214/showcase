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

package com.smartgwt.sample.showcase.client.dataintegration.java.hibernate;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.QueueSentCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class MasterDetailHibernateSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows a simple way to implement an updatable parent-child relationship with Smart GWT, " +
            "the Smart GWT Server and Hibernate. As can be seen from the various source tabs, \"Order\" and \"OrderItem\" are related via a unidirectional " +
            "Set collection in Hibernate. The \"Order\" DataSource also declares its items field as being of type <b>masterDetail_orderItemHB</b>, which tells " +
            "Smart GWT to use that DataSource as the schema when processing the detail lines. With this configuration in place, creating a UI capable of updating " +
            "across this parent-child association becomes extremely easy - only two lines of Smart GWT code, beyond the creation and layout of the visual " +
            "components themselves, is required.</p>" +
            "<p>Click a record in the top grid to see the order's details and the associated detail lines in the form and grid below. Edit the order " +
            "information using this screen (both header and detail) by double-clicking the grid to edit the details. When \"Save\" is clicked, Smart GWT will submit the " +
            "master and detail information together, and Hibernate will save all changes as a single operation.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            MasterDetailHibernateSample panel = new MasterDetailHibernateSample();
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
        Label ordersLabel = new Label("Order");
        ordersLabel.setWidth("90%");
        ordersLabel.setHeight(25);
        ordersLabel.setBaseStyle("exampleSeparator");

        DataSource dataSource = DataSource.get("masterDetail_orderHB");
        DataSource orderItemDS = DataSource.get("masterDetail_orderItemHB");

        //create the order form
        final DynamicForm orderForm = new DynamicForm();
        orderForm.setDataSource(dataSource);

        TextItem orderIdItem = new TextItem("orderID");
        orderIdItem.setTitle("Order ID");
        orderIdItem.setDisabled(true);

        TextItem customerNameItem = new TextItem("customerName");
        customerNameItem.setTitle("Customer Name");

        DateItem orderDateItem = new DateItem();
        orderDateItem.setName("orderDate");
        orderDateItem.setTitle("Order Date");

        orderForm.setFields(orderIdItem, customerNameItem, orderDateItem);

        //the order items list grid
        final ListGrid orderItemsList = new ListGrid();

        //the order list grid
        ListGrid ordersList = new ListGrid();
        ordersList.setHeight(170);
        ordersList.setWidth(500);
        ordersList.setDataSource(dataSource);
        ordersList.setAutoFetchData(true);

        ListGridField orderIdField = new ListGridField("orderID");
        orderIdField.setWidth("25%");

        ListGridField customerNameField = new ListGridField("customerName");

        ListGridField orderDateField = new ListGridField("orderDate");
        orderDateField.setWidth("25%");

        ordersList.setFields(orderIdField, customerNameField, orderDateField);
        ordersList.addSelectionChangedHandler(new SelectionChangedHandler() {
            public void onSelectionChanged(SelectionEvent event) {
                Record record = event.getRecord();
                orderForm.editRecord(record);
                orderItemsList.setData(record.getAttributeAsRecordArray("items"));
            }
        });

        Label orderDetailsLabel = new Label("Order Details");
        orderDetailsLabel.setWidth("90%");
        orderDetailsLabel.setBaseStyle("exampleSeparator");

        orderItemsList.setHeight(130);
        orderItemsList.setDataSource(orderItemDS);
        orderItemsList.setCanEdit(true);
        orderItemsList.setSaveLocally(true);
        orderItemsList.setSaveByCell(true);

        ListGridField itemDescriptionField = new ListGridField("itemDescription");
        ListGridField quantityField = new ListGridField("quantity");
        ListGridField unitPriceField = new ListGridField("unitPrice");

        orderItemsList.setFields(itemDescriptionField, quantityField, unitPriceField);

        IButton saveButton = new IButton("Save");
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                orderForm.setValue("items", orderItemsList.getRecords());
                orderForm.saveData();
            }
        });

        VLayout layout = new VLayout(5);
        layout.setWidth(500);
        layout.addMember(ordersLabel);
        layout.addMember(ordersList);
        layout.addMember(orderDetailsLabel);
        layout.addMember(orderForm);
        layout.addMember(orderItemsList);
        layout.addMember(saveButton);

        final ServerCountLabel serverCountLabel = new ServerCountLabel();

        layout.addMember(serverCountLabel);

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
                new SourceEntity("server/Order.java", JAVA, "source/beans/Order.java.html", true),
                new SourceEntity("server/OrderItem.java", JAVA, "source/beans/OrderItem.java.html", true),
                new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true)
        };
    }
}