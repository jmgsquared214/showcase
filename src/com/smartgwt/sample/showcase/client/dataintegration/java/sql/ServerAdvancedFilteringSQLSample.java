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

package com.smartgwt.sample.showcase.client.dataintegration.java.sql;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class ServerAdvancedFilteringSQLSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Use the FilterBuilder to construct queries of arbitrary complexity. " +
            "The FilterBuilder, and the underlying <code>AdvancedCriteria</code> system, support building queries with subclauses nested to any depth. " +
            "Add clauses to your query with the \"+\" icon; add nested subclauses with the \"+()\" button. " +
            "Click \"Filter\" to see the result in the ListGrid.</p>" +
            "<p>Note that this example is backed by an SQL dataSource; Smart GWT Server is automatically generating the SQL queries " +
            "required to implement the filters that the FilterBuilder can assemble. This works adaptively and seamlessly with client-side " +
            "Advanced Filtering: the generated SQL query will yield exactly the same resultset as the client-side filtering. " +
            "This means Smart GWT is able to switch to client-side filtering when its cache is full, giving a more responsive, more scalable " +
            "application.</p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the Server Advanced filtering feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Enterprise Edition</a>.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#filterbuilder_sql\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return Showcase.hasServerAdvancedFiltering();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new ServerAdvancedFilteringSQLSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        DataSource dataSource = DataSource.get("worldDS");

        final FilterBuilder advancedFilter = new FilterBuilder();
        advancedFilter.setDataSource(dataSource);

        final ListGrid countryGrid = new ListGrid();
        countryGrid.setWidth(700);
        countryGrid.setHeight(300);
        countryGrid.setAlternateRecordStyles(true);
        countryGrid.setDataSource(dataSource);
        countryGrid.setAutoFetchData(true);

        ListGridField countryField = new ListGridField("countryName");
        ListGridField continentField = new ListGridField("continent");

        final NumberFormat nf = NumberFormat.getDecimalFormat();

        ListGridField populationField = new ListGridField("population");
        populationField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(record == null) return null;
                return nf.format(((Number)value).doubleValue());
            }
        });

        ListGridField areaField = new ListGridField("area");
        areaField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(record == null) return null;
                return nf.format(((Number)value).doubleValue());
            }
        });

        ListGridField gdpField = new ListGridField("gdp");
        gdpField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(record == null) return null;
                return nf.format(((Number)value).doubleValue());
            }
        });

        ListGridField independenceField = new ListGridField("independence");

        countryGrid.setFields(countryField, continentField, populationField, areaField, gdpField, independenceField);

        IButton filterButton = new IButton("Filter");
        filterButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryGrid.filterData(advancedFilter.getCriteria());
            }
        });

        VStack layout = new VStack(10);
        layout.addMember(advancedFilter);
        layout.addMember(filterButton);
        layout.addMember(countryGrid);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}