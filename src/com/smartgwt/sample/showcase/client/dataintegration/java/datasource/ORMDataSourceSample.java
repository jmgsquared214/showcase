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

package com.smartgwt.sample.showcase.client.dataintegration.java.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class ORMDataSourceSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows an entirely custom DataSource that connects Smart GWT Server to Hibernate. " +
            "It is a very simple implementation created by extending <code>BasicDataSource</code> and implementing the four core CRUD methods. In this case, the DataSource " +
            "implementation handles a single Hibernate entity. Features like data pagination, server-side sorting and filtering are not implemented here.</p>" +
            "<p>Creating an equivalent adapter for Toplink or Ibatis or other ORM solutions would be a fairly simple matter of replacing the Hibernate-specific code in this example " +
            "with the equivalent specifics from the other ORM system.</p>" +
            "<p>As with the other custom DataSource examples, note how the <code>ORMDataSource.java</code> code deals entirely in native Java objects - even entirely custom DataSources benefit from Smart GWT Server's robust and comprehensive JavaScript&#8596;Java translation.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            ORMDataSourceSample panel = new ORMDataSourceSample();
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
        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(DataSource.get("ormDataSource_country"));
        listGrid.setCanEdit(true);
        listGrid.setCanRemoveRecords(true);
        listGrid.setDataFetchMode(FetchMode.LOCAL);
        listGrid.setAutoFetchData(true);
        listGrid.setUseAllDataSourceFields(true);

        IButton newButton = new IButton("Add New");
        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.startEditingNew();
            }
        });


        VLayout layout = new VLayout(15);
        layout.addMember(listGrid);
        layout.addMember(newButton);

        return layout;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
                new SourceEntity("ORMDataSource.java", JAVA, "source/datasource/ORMDataSource.java.html", true),
                new SourceEntity("Country.java", JAVA, "source/beans/Country.java.html", true)
        };
    }
    public String getIntro() {
        return DESCRIPTION;
    }
}