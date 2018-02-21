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

package com.smartgwt.sample.showcase.client.dataintegration.java.tree;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class TreeReparentSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Dragging employees between managers in this tree automatically saves the " +
            "new relationship to a DataSource, without writing any code. Make changes, then reload the page: The changes persist.</p>" +
            "<p>This example uses a Relational SQL DataSource connector.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            TreeReparentSample panel = new TreeReparentSample();
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

    public Canvas getViewPanel() {
        final TreeGrid treeGrid = new TreeGrid();
        treeGrid.setWidth(500);
        treeGrid.setHeight(500);
        treeGrid.setDataSource(DataSource.get("employees"));
        treeGrid.setCanDragRecordsOut(true);
        treeGrid.setCanAcceptDroppedRecords(true);
        treeGrid.setAutoFetchData(true);

        return treeGrid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}