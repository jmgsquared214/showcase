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

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class AutomaticTransactionManagementSample extends ShowcasePanel {

    private static final String DESCRIPTION = "Drag multiple records from the left-hand grid to the right.  Smart GWT will " +
            "send the updates to the server in a single queue; Smart GWT Server will " +
            "automatically treat that queue as a single database transaction.  This is the " +
            "default behavior, and requires no code or special configuration to enable it. However, if required, " +
            "very flexible, fine-grained control over transactions is possible, " +
            "through configuration, code or a combination of the two.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            AutomaticTransactionManagementSample panel = new AutomaticTransactionManagementSample();
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
        final ListGrid fullListGrid = new ListGrid();
        fullListGrid.setWidth(300);
        fullListGrid.setHeight(224);
        fullListGrid.setAlternateRecordStyles(true);
        fullListGrid.setCanDragRecordsOut(true);
        fullListGrid.setDragDataAction(DragDataAction.MOVE);
        fullListGrid.setDataSource(DataSource.get("supplyItem"));
        fullListGrid.setAutoFetchData(true);
        fullListGrid.setFields(new ListGridField[] {
            new ListGridField("itemName"),
            new ListGridField("SKU"),
            new ListGridField("category")
        });

        final ListGrid sundriesListGrid = new ListGrid();
        sundriesListGrid.setWidth(300);
        sundriesListGrid.setHeight(224);
        sundriesListGrid.setAlternateRecordStyles(true);
        sundriesListGrid.setCanAcceptDroppedRecords(true);
        sundriesListGrid.setDataSource(DataSource.get("supplyItem"));
        sundriesListGrid.setFields(new ListGridField[] {
            new ListGridField("itemName"),
            new ListGridField("SKU"),
            new ListGridField("category")
        });
        sundriesListGrid.fetchData(new Criteria("category", "Office Machine Sundries"));

        HLayout layout = new HLayout(15);
        layout.addMember(fullListGrid);
        layout.addMember(sundriesListGrid);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
