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

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.EditFailedEvent;
import com.smartgwt.client.widgets.grid.events.EditFailedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class JDBCOperationsSample extends ShowcasePanel {

    private static final String DESCRIPTION = "User-written operations - in this example, hand-crafted JDBC updates - can be " +
            "included in Smart GWT automatic transactions, and will be committed or rolled " +
            "back alongside the normal Smart GWT operations.<p/>" +
            "Edit rows in the grid, then click \"Good Save\".  The changes will be " +
            "persisted to the database as part of a queue that also includes a user-written " +
            "JDBC update to a \"lastChanged\" table; the DMI (Direct Method Invocation) method has been written to use the " +
            "<code>JDBCOperations.java</code> tab.  The example will then fetch the current " +
            "value from the lastUpdated table and display it in the blue label, showing " +
            "that it has been updated.<p/>" +
            "Now make further changes and click \"Bad Save\".  This causes a deliberately " +
            "broken version of the user-written JDBC update to be run, resulting in a SQL error " +
            "and a rolled-back transaction (and an error dialog referring to an unknown column). " +
            "Note that the changes have not been saved (they " +
            "are still presented in blue, to show that they are pending) and the \"last updated\"" +
            "label has not changed; the entire transaction, both Smart GWT requests and " +
            "user-written query, have been rolled back. Now click \"Good Save\", and note that the pending " +
            "changes will be persisted and the \"last updated\" label will change to reflect this.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            JDBCOperationsSample panel = new JDBCOperationsSample();
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
        final DataSource lastUpdated = DataSource.get("lastUpdated");

        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth(500);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(DataSource.get("countryTransactions"));
        listGrid.setAutoFetchData(true);
        listGrid.setCanEdit(true);
        listGrid.setAutoSaveEdits(false);
        listGrid.setFields(new ListGridField[] {
            new ListGridField("countryName"),
            new ListGridField("capital"),
            new ListGridField("continent"),
            new ListGridField("gdp")
        });

        final Label lastUpdatedLabel = new Label();
        lastUpdatedLabel.setBackgroundColor("#aabbff");
        lastUpdatedLabel.setHeight(20);
        lastUpdatedLabel.setWidth(500);

        listGrid.addEditCompleteHandler(new EditCompleteHandler() {
            public void onEditComplete(EditCompleteEvent event) {
                lastUpdated.fetchData(null, new DSCallback() {
                    public void execute(DSResponse response, Object rawData, DSRequest request) {
                        lastUpdatedLabel.setContents(response.getData()[0].getAttribute("lastUpdatedTime"));
                    }
                });
            }
        });
        listGrid.addEditFailedHandler(new EditFailedHandler() {
            public void onEditFailed(EditFailedEvent event) {
                lastUpdated.fetchData(null, new DSCallback() {
                    public void execute(DSResponse response, Object rawData, DSRequest request) {
                        lastUpdatedLabel.setContents(response.getData()[0].getAttribute("lastUpdatedTime"));
                    }
                });
            }
        });

        lastUpdated.fetchData(null, new DSCallback() {
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                lastUpdatedLabel.setContents(response.getData()[0].getAttribute("lastUpdatedTime"));
            }
        });

        IButton goodSaveButton = new IButton("Good Save");
        goodSaveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RPCManager.startQueue();
                listGrid.saveAllEdits();
                Record r = new Record();
                r.setAttribute("pk", 1);
                DSRequest req = new DSRequest();
                req.setAttribute("operationId", "goodJDBCUpdate");
                lastUpdated.updateData(r, null, req);
                RPCManager.sendQueue();
            }
        });

        IButton badSaveButton = new IButton("Bad Save");
        badSaveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RPCManager.startQueue();
                listGrid.saveAllEdits();
                Record r = new Record();
                r.setAttribute("pk", 1);
                DSRequest req = new DSRequest();
                req.setAttribute("operationId", "badJDBCUpdate");
                lastUpdated.updateData(r, null, req);
                RPCManager.sendQueue();
            }
        });

        HLayout buttonLayout = new HLayout(15);
        buttonLayout.addMember(goodSaveButton);
        buttonLayout.addMember(badSaveButton);

        VLayout layout = new VLayout(15);
        layout.addMember(listGrid);
        layout.addMember(lastUpdatedLabel);
        layout.addMember(buttonLayout);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("JDBCOperations.java", JAVA, "source/transactions/JDBCOperations.java.html", true)
            };
    }
}
