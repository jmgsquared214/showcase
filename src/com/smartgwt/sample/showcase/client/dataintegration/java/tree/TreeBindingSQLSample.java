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
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class TreeBindingSQLSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example uses a Relational DataSource connector as the DataSource for " +
    		"the Tree.</p>" +
            "<p>Begin opening folders and note that the browser is contacting the server to fetch more data. Trees can load data" +
            " one folder at a time. When a folder is opened for the first time, the tree asks the server for the children" +
            " of the node just opened by passing the unique id of the parent as search criteria. </p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            TreeBindingSQLSample panel = new TreeBindingSQLSample();
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
        treeGrid.setLeft(50);
        treeGrid.setTop(50);
        treeGrid.setWidth(500);
        treeGrid.setHeight(500);
        treeGrid.setDataSource(DataSource.get("employees"));

        TreeGridField nameField = new TreeGridField("Name");
        nameField.setTreeField(true);

        treeGrid.setFields(nameField, new TreeGridField("Job"), new TreeGridField("Email"));

        treeGrid.addDrawHandler(new DrawHandler() {
            public void onDraw(DrawEvent event) {
                // fetch top-level nodes to populate treeGrid
                treeGrid.fetchData();
            }
        });

        return treeGrid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }    
}
