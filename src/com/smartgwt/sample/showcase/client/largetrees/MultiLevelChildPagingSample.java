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
import com.smartgwt.client.widgets.tree.ResultTree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class MultiLevelChildPagingSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Server logic can return multiple levels of the tree in response to a single " +
        "request when using child paging.</p>" +
        "<p>In the tree below, the folders \"Root #4\" and \"First #5\" have been returned " +
        "by the server already open.  The server included the children of these nodes to " +
        "avoid the need for the tree to immediately contact the server again to load " +
        "children.</p>" +
        "<p>However, both of these nodes have a very large number of children, so they " +
        "only returned a portion of their children, using the <code>childCountProperty</code> " +
        "to tell the tree the total number of children.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            MultiLevelChildPagingSample panel = new MultiLevelChildPagingSample();
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
        treeGrid.setDataSource(DataSource.get("hugeTreeOpenNodes"));
        treeGrid.setDataFetchMode(FetchMode.PAGED);
        treeGrid.setProgressiveLoading(true);

        final ResultTree resultTreeProperties = new ResultTree();
        resultTreeProperties.setOpenProperty("isOpen");
        resultTreeProperties.setChildrenProperty("children");
        resultTreeProperties.setCanReturnOpenFolders(true);
        treeGrid.setDataProperties(resultTreeProperties);

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

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
            new SourceEntity("HugeTreeOpenNodesDMI.java", JAVA, "source/datasource/HugeTreeOpenNodesDMI.java.html", true)
        };
    }
}
