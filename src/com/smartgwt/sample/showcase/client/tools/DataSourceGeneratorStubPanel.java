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

public class DataSourceGeneratorStubPanel extends ShowcasePanel {

    private static final String DESCRIPTION = "<p><b>Note :</b>This Showcase doesn't include a live demo of the DataSource " +
            "Generator because it is publicly accessible by multiple users as well as sandboxing reasons. This is enabled" +
            " in the actual distribution.</p>" +
            "<p>Smart GWT's Visual Builder tool provides an extremely easy and completely codeless way to " +
            "create DataSources for instantly connecting to existing database tables. Just click the \"New\" button, " +
            "select \"Existing SQL Table\", and the Database Browser will show all tables, column details and actual " +
            "data. Select a table, and Visual Builder will create a fully-functioning DataSource that can perform all four" +
            " CRUD operations on that table, including complex searches enabled by Smart GWT's <code>AdvancedCriteria</code> system.</p>" +
            "<p>The Visual Builder also supports a completely codeless way to create DataSources for instantly connecting to" +
            " existing Hibernate mapped entitites. Just click the \"New\" button, select \"Hibernate Bean\", and the" +
            " Hibernate Browser will show all mapped entities, properties and even the data in the actual table to which the" +
            " entity is mapped. Select an entity, and Visual Builder will create a fully-functioning DataSource that can" +
            " perform all four CRUD operations via Hibernate on that entity, including complex searches enabled by the " +
            "<code>AdvancedCriteria</code> system.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            DataSourceGeneratorStubPanel panel = new DataSourceGeneratorStubPanel();
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

        Img img = new Img("ds-wizard.png", 653, 687);
        layout.addMember(img);
        return layout;
    }
    
    public String getIntro() {
        return DESCRIPTION;
    }
}