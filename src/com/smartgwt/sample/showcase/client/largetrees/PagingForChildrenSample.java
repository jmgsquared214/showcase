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

package com.smartgwt.sample.showcase.client.largetrees;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class PagingForChildrenSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>SmartClient supports loading children as they are scrolled into view, which is " +
        "needed for very large trees where the number of children under a single node can " +
        "be very large.</p>" +
        "<p>In the tree below, there are thousands of root-level nodes in the dataset " +
        "stored on the server.  Scroll down to cause more nodes to be loaded from the " +
        "server.</p>" +
        "<p>Open the folder \"Root #4\", to reveal another large set of children which can " +
        "be incrementally loaded.  Within these children, open \"First #5\" to reveal " +
        "another large set of children.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            PagingForChildrenSample panel = new PagingForChildrenSample();
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
        treeGrid.setDataSource(DataSource.get("hugeTree"));
        treeGrid.setDataFetchMode(FetchMode.PAGED);

        treeGrid.setAutoFetchData(true);
        treeGrid.setShowFilterEditor(true);

        // Customize appearance.
        TreeGridField nameField = new TreeGridField("name");
        nameField.setCanFilter(true);
        treeGrid.setFields(nameField);
        treeGrid.setWidth(500);
        treeGrid.setHeight(400);
        treeGrid.setShowOpenIcons(false);
        treeGrid.setShowDropIcons(false);
        treeGrid.setNodeIcon("pieces/16/cube_blue.png");
        treeGrid.setFolderIcon("pieces/16/cubes_blue.png");
        treeGrid.setClosedIconSuffix("");
        treeGrid.setShowConnectors(true);
        return treeGrid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
