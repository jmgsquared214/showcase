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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class ReusableORMDataSourceSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows an entirely custom DataSource that connects Smart GWT Server to Hibernate (note that this is just an example of the principles involved - Smart GWT Server's built-in Hibernate " +
            "support is considerably more sophisticated than the simple adapter shown here). It is created by extending <code>BasicDataSource</code> and implementing the four core CRUD methods. In this case, the DataSource connection sends requests to Hibernate " +
            "<code>Criteria</code> queries and the <code>saveOrUpdate</code> method.</p>" +
            "<p>This implementation, though simple, is fully functional and could be used unchanged in a real application. It supports all four CRUD operations, plus data pagination, server-side sorting and filtering, client cache synchronization, " +
            "and of course it is actually persisting the data to a real database. In this case, a single DataSource implementation handles two different entities using Reflection. " +
            "Note that this is a simplified version of the built-in connector which handles <code>AdvancedCriteria</code> filtering.</p>" +
            "<p>As with the other custom DataSource examples, note how the <code>ReusableORMDataSourceSample.java</code> code deals entirely in native Java objects - even entirely custom DataSources benefit from Smart GWT Server's robust and comprehensive JavaScript&#8596;Java translation.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            ReusableORMDataSourceSample panel = new ReusableORMDataSourceSample();
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
        final DynamicForm dynamicForm = new DynamicForm();
        dynamicForm.setWrapItemTitles(false);
        final SelectItem dsSelect = new SelectItem("dataSource", "Select a data source");
        dsSelect.setValueMap("reusableORMDataSource_supplyItem", "reusableORMDataSource_country");
        dsSelect.setDefaultToFirstOption(true);
        dsSelect.setWidth(210);
        dsSelect.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                listGrid.setDataSource(DataSource.get(dsSelect.getValue().toString()));
                listGrid.filterData();
            }
        });
        dynamicForm.setFields(dsSelect);

        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(DataSource.get("reusableORMDataSource_supplyItem"));
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
        layout.addMember(dynamicForm);
        layout.addMember(listGrid);
        layout.addMember(newButton);

        return layout;
    }

    public SourceEntity[] getSourceUrls() {
    // Do not remove commented lines.
    // Forces GenerateSourceFiles to generate DataURLRecords for both data sources.
    // Generated DataURLRecords are used in 'View source' window for showing data source configuration.
//        DataSource.get("reusableORMDataSource_supplyItem");
//        DataSource.get("reusableORMDataSource_country");
        return new SourceEntity[] {
                new SourceEntity("ReusableORMDataSource.java", JAVA, "source/datasource/ReusableORMDataSource.java.html", true),
                new SourceEntity("SupplyItemHB.java", JAVA, "source/beans/SupplyItemHB.java.html", true),
                new SourceEntity("Country.java", JAVA, "source/beans/Country.java.html", true)
        };
    }

    public String getIntro() {
        return DESCRIPTION;
    }

}