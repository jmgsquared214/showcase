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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class MultiLevelLoadOnDemandSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Server logic can return multiple levels of the tree in response to a single " +
        "request when using load on demand.</p>" +
        "<p>In the tree below, the nodes \"Charles Madigen\" and \"Tammy Plant\" have been " +
        "returned by the server already open.  The server included the children of these " +
        "nodes to avoid the need for the tree to immediately contact the server again to " +
        "load children.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            MultiLevelLoadOnDemandSample panel = new MultiLevelLoadOnDemandSample();
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
        treeGrid.setDataSource(DataSource.get("employeesOpenNodes"));
        treeGrid.setAutoFetchData(true);

        final Tree resultTreeProperties = new Tree();
        resultTreeProperties.setOpenProperty("IsOpen");
        resultTreeProperties.setChildrenProperty("DirectReports");
        treeGrid.setDataProperties(resultTreeProperties);

        // Customize appearance.
        treeGrid.setWidth(500);
        treeGrid.setHeight(400);
        treeGrid.setNodeIcon("icons/16/person.png");
        treeGrid.setFolderIcon("icons/16/person.png");
        treeGrid.setShowOpenIcons(false);
        treeGrid.setShowDropIcons(false);
        treeGrid.setClosedIconSuffix("");
        return treeGrid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
            new SourceEntity("EmployeesOpenNodesDMI.java", JAVA, "source/datasource/EmployeesOpenNodesDMI.java.html", true)
        };
    }
}
