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
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class DataSourceGeneratorPanel extends ShowcasePanel {
    
    private static final String DESCRIPTION = "<p>The Smart GWT EE DataSource generator is the key starting point for " +
            "integrating with any pre-existing system: it generates DataSources from existing SQL tables or Java beans " +
            "(Hibernate or otherwise), and in the case of SQL or Hibernate the generated DataSources are immediately fully " +
            "functional for all CRUD operations, including advanced filtering.</p>" +
            "<p>To generate a DataSource from an existing database table, click the \"DataSource Generator\" button and in " +
            "the \"List of table name\" field enter \"ANIMALS\" or \"TEAMMEMBERS\". Make sure the \"Overwite files if they exist\" " +
            "checkbox is selected as someone else might have generated the DataSource previously. Now click the \"OK\" button and the DataSource is generated " +
            " from the table definition</p>" +
            "<p>The DataSource that has just been generated can be viewed by opening the <b>DataSource Admin Console</b> from the side navigation</p>" +
            "<p>If generating a DataSource from an existing server side Javabean is required, repeat the above steps but instead of entering a table name" +
            " in the generator, enter the name of a server side bean. For example : <b>com.smartgwt.sample.showcase.server.SupplyItemBean</b></p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled in this SDK because it requires the Batch DataSource Generator Feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Enterprise Edition</a>. You can still generate " +
                    "DataSources individually using the \"New\" button in the Visual Builder.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DataSourceGeneratorPanel();
        }
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        return null;
    }
}