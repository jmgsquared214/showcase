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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.QueueSentCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCQueueCallback;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class QueuedMasterDetailAddSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>This example makes use of Smart GWT Server's support for setting DSRequest properties dynamically" +
            " at runtime, based on responses to requests earlier in the same queue.</p>" +
            "<p>Edit the order header details, then add one or more lines. When \"Save Order\" is clicked, Smart GWT will send" +
            " multiple DSRequests to the server - one to save the header and one each for however many lines were entered, but" +
            " it will queue them so they are all sent as one HTTP request.</p>" +
            "<p>The server will set the \"orderID\" property on each line to the unique sequence value assigned to the order" +
            " header when it was written to the database. This happens as a result of the <code><value></code> tag in the" +
            " \"queuedAdd_orderItem\" DataSource definition. This is done by specifying <code>&lt;value&gt;</code> (and the" +
            " similar property <code>&lt;criteria&gt;</code>) using the Velocity Template Language, so the support is very flexible.</p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the Transaction Chaining feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Enterprise Edition</a>.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#transactions_queued_md\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return Showcase.hasTransactionChaining();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new QueuedMasterDetailAddSample();
        }
    }

    public Canvas getViewPanel() {
        DataSource queuedAddOrderDS = DataSource.get("queuedAdd_order");
        DataSource queuedAddOrderItemDS = DataSource.get("queuedAdd_orderItem");
        DataSource supplyCategoryDS = DataSource.get("supplyCategory");
        DataSource supplyItemDS = DataSource.get("supplyItem");

        final DynamicForm form = new DynamicForm();
        form.setWidth(300);
        form.setDataSource(queuedAddOrderDS);
        form.setUseAllDataSourceFields(true);
        form.setWrapItemTitles(false);

        final ListGrid listGrid = new ListGrid();
        listGrid.setHeight(224);
        listGrid.setWidth(400);
        listGrid.setDataSource(queuedAddOrderItemDS);
        listGrid.setCanEdit(true);
        listGrid.setAutoSaveEdits(false);

        ListGridField qtyField = new ListGridField("quantity", "Qty", 50);

        ListGridField categoryField = new ListGridField("categoryName", "Category");
        SelectItem categorySelectItem = new SelectItem();
        categorySelectItem.setOptionDataSource(supplyCategoryDS);
        categoryField.setEditorProperties(categorySelectItem);
           
        categoryField.addChangedHandler(new com.smartgwt.client.widgets.grid.events.ChangedHandler() {  
            public void onChanged(com.smartgwt.client.widgets.grid.events.ChangedEvent event) {  
                listGrid.clearEditValue(event.getRowNum(), "itemName");  
            }  
        });  

        ListGridField itemField = new ListGridField("itemName", "Item");
        SelectItem itemSelectItem = new SelectItem();
        itemSelectItem.setOptionDataSource(supplyItemDS);
        itemSelectItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				String category = (String) listGrid.getEditedCell(listGrid.getEditRow(), "categoryName");  
                return new Criteria("category", category);
            }
		});
        itemField.setEditorProperties(itemSelectItem);

        listGrid.setFields(qtyField, categoryField, itemField);

        IButton addButton = new IButton("Add Order Line");
        addButton.setMinWidth(110);
        addButton.setAutoFit(true);
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Map defaults = new HashMap();
                defaults.put("quantity", 1);
                listGrid.startEditingNew(defaults);
            }
        });

        IButton saveButton = new IButton("Save Order");
        saveButton.setWidth(100);
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RPCManager.startQueue();
                form.saveData();
                listGrid.saveAllEdits();
                RPCManager.sendQueue(new RPCQueueCallback() {
					public void execute(RPCResponse[] responses) {
		                form.editNewRecord();
		                listGrid.setData(new ListGridRecord[0]);
		                SC.say("Order saved in single batch.");
					}
				});
            }
        });

        HLayout hLayout = new HLayout(10);
        hLayout.addMember(addButton);
        hLayout.addMember(saveButton);

        VLayout layout = new VLayout(20);
        layout.setAutoHeight();
        layout.addMember(form);
        layout.addMember(listGrid);
        layout.addMember(hLayout);

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
}