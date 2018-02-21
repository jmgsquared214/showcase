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
package com.smartgwt.sample.showcase.client.componentXML;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.LoadScreenCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class VariousControls extends ShowcasePanel {
    private static final String DESCRIPTION =
            "Demonstration of several form controls. <P>" +
            "For the ComboBoxes at the bottom, start typing in either " +
            "field to see a list of matching options. The field labelled \"Item Name\" " +
            "retrieves options dynamically from the SupplyItem DataSource.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
        	VariousControls panel = new VariousControls();
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
    
    @Override
    protected boolean isTopIntro() {
        return false;
    }
    
    public Canvas getViewPanel() {
        final Canvas layout = new Canvas();
        RPCManager.loadScreen("variousControls", new LoadScreenCallback() {
            @Override
            public void execute() {
                layout.addChild(this.getScreen());
            }
        });
        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("variousControls.ui.xml", XML, "source/variousControls.ui.xml.html", true),
            new SourceEntity("supplyItem.ds.xml", XML, "source/supplyItem.ds.xml.html", true),
            new SourceEntity("supplyCategory.ds.xml", XML, "source/supplyCategory.ds.xml.html", true)
        };
    }
}
