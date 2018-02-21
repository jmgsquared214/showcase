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

package com.smartgwt.sample.showcase.client.dataintegration.java.hibernate;

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


public class AutoDeriveHibernateSample extends ShowcasePanel {
    private static final String DESCRIPTION = "With pre-existing Hibernate beans, Smart GWT can automatically derive fully functional " +
            "DataSources given just the Java classname of the Hibernate Bean.  The grid below is connected " +
            "to a Hibernate-managed bean via the simple declarations made in \"supplyItem.ds.xml\" - No other " +
            "configuration or Java code is required beyond the bean itself and the Hibernate mapping, which are " +
            "samples intended to represent a pre-existing Hibernate bean.<p/>" +
            "To search, use the controls above the grid's header. Note that data paging is automatically " +
            "enabled - just scroll down to load data on demand. Click on the red icon next to each record to " +
            "delete it. Click on a record to edit it and click \"Add New\" to add a new record.  Note that the " +
            "editing controls are type sensitive: a date picker appears for the \"Next Shipment\" field, and " +
            "the \"Units\" field shows a picklist because its Java type is an Enum.<p/>" +
            "Use DMI to add business logic that takes place before and after Hibernate operations to " +
            "enforce security or add additional data validation rules.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            AutoDeriveHibernateSample panel = new AutoDeriveHibernateSample();
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
        listGrid.setWidth(900);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(DataSource.get("supplyItemHBAutoDerive"));
        listGrid.setCanEdit(true);
        listGrid.setCanRemoveRecords(true);
        listGrid.setDataFetchMode(FetchMode.LOCAL);
        listGrid.setAutoFetchData(true);
        listGrid.setUseAllDataSourceFields(true);
        listGrid.setShowFilterEditor(true);

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
                new SourceEntity("SupplyItemHB.java", JAVA, "source/beans/SupplyItemHB.java.html", true)
        };
    }
    public String getIntro() {
        return DESCRIPTION;
    }
}