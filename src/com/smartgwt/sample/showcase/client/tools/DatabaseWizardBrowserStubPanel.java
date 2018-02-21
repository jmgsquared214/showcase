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

package com.smartgwt.sample.showcase.client.tools;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class DatabaseWizardBrowserStubPanel extends ShowcasePanel {

    private static final String DESCRIPTION = "<p><b>Note :</b>This Showcase doesn't include a live demo because it is " +
            "publicly accessible by multiple users as well as sandboxing reasons.</p>" +
            "<p>Smart GWT's Visual Builder tool provides an extremely easy and completely codeless way to create DataSources " +
            "for instantly connecting to existing database tables. Just click the \"New\" button, select \"Existing SQL Table\"," +
            " and the Database Browser will show all tables, column details and the actual data. Select a table, and Visual" +
            " Builder will create a fully-functioning DataSource that can perform all four CRUD operations on that table, " +
            "including - for systems with the Enterprise license - complex searches enabled by the Smart GWT <code>Advanced Criteria</code>" +
            " system..</p>" +
            "<p>The Database Browser connects to any configured database and allows you to browse through both schema and data." +
            " Once the table to connect to has been located, simply click it and then click \"Next\"; Visual Builder will " +
            "instantly create a DataSource and access the DataSource Editor for fine tuning</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            DatabaseWizardBrowserStubPanel panel = new DatabaseWizardBrowserStubPanel();
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

        VLayout layout = new VLayout(15);
        layout.setWidth100();

        Img img = new Img("vb_DatabaseBrowser.png", 1015, 625);
        layout.addMember(img);
        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}