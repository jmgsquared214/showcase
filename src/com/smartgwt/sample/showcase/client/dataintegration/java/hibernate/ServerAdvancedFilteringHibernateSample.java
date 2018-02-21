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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class ServerAdvancedFilteringHibernateSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Use the FilterBuilder to construct queries of arbitrary complexity. " +
            "The FilterBuilder, and the underlying <code>AdvancedCriteria</code> system, support building queries with subclauses nested to any depth. " +
            "Add clauses to the query with the \"+\" icon; add nested subclauses with the \"+()\" button. " +
            "Click \"Filter\" to see the result in the ListGrid.</p>" +
            "<p>Note that this example is backed by a Hibernate DataSource. The Smart GWT Server is automatically generating the Hibernate Criteria Queries " +
            "(including database-specific SQL where necessary) to implement the filters that the FilterBuilder can assemble. This works adaptively and seamlessly with client-side " +
            "Advanced Filtering. The generated SQL query will yield exactly the same resultset as the client-side filtering. " +
            "This means Smart GWT is able to switch to client-side filtering when its cache is full, giving a more responsive, more scalable " +
            "application.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            ServerAdvancedFilteringHibernateSample panel = new ServerAdvancedFilteringHibernateSample();
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

        DataSource dataSource = DataSource.get("supplyItemHB");

        final FilterBuilder advancedFilter = new FilterBuilder();
        advancedFilter.setDataSource(dataSource);

        final ListGrid itemGrid = new ListGrid();
        itemGrid.setWidth(550);
        itemGrid.setHeight(225);
        itemGrid.setAlternateRecordStyles(true);
        itemGrid.setDataSource(dataSource);
        itemGrid.setAutoFetchData(true);

        ListGridField itemField = new ListGridField("itemName");
        itemField.setWidth("35%");

        ListGridField skuField = new ListGridField("SKU");
        skuField.setWidth("15%");

        ListGridField descriptionField = new ListGridField("description");
        descriptionField.setWidth("35%");

        ListGridField unitCostField = new ListGridField("unitCost");
        unitCostField.setWidth("15%");

        itemGrid.setFields(itemField, skuField, descriptionField, unitCostField);

        IButton filterButton = new IButton("Filter");
        filterButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                itemGrid.filterData(advancedFilter.getCriteria());
            }
        });

        VStack layout = new VStack(10);
        layout.addMember(advancedFilter);
        layout.addMember(filterButton);
        layout.addMember(itemGrid);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}