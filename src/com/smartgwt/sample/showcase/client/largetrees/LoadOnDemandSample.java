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
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class LoadOnDemandSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Begin opening folders and note the prompt which briefly appears during server " +
        "fetches.  Trees can load data one folder at a time.  When a folder is opened for " +
        "the first time, the tree asks the server for the children of the node just opened " +
        "by passing the unique id of the parent as search criteria.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            LoadOnDemandSample panel = new LoadOnDemandSample();
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
        treeGrid.setDataSource(DataSource.get("employees"));
        treeGrid.setAutoFetchData(true);

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
}
