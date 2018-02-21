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

package com.smartgwt.sample.showcase.client.dataintegration.java.jpa;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class ServerAdvancedFilteringJPASample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Use the FilterBuilder to construct queries of arbitrary complexity. " +
            "The FilterBuilder, and the underlying <code>AdvancedCriteria</code> system, support building queries with subclauses nested to any depth. " +
            "Add clauses to the query with the \"+\" icon; add nested subclauses with the \"+()\" button. " +
            "Click \"Filter\" to see the result in the ListGrid.</p>" +
            "<p>Note that this example is backed by a JPA 2.0 DataSource. Smart GWT Server is automatically generating the Hibernate Criteria Queries " +
            "(including database-specific SQL where necessary) to implement the filters that the FilterBuilder can assemble. This works adaptively and seamlessly with client-side " +
            "Advanced Filtering. The generated SQL query will yield exactly the same resultset as the client-side filtering. " +
            "This means Smart GWT is able to switch to client-side filtering when its cache is full, giving a more responsive, more scalable " +
            "application.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            ServerAdvancedFilteringJPASample panel = new ServerAdvancedFilteringJPASample();
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

        DataSource dataSource = DataSource.get("worldJPA2");

        final FilterBuilder advancedFilter = new FilterBuilder();
        advancedFilter.setDataSource(dataSource);

        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(dataSource);
        listGrid.setAutoFetchData(true);
        listGrid.setShowFilterEditor(true);
        listGrid.setDataPageSize(50);
        listGrid.setCanEdit(true);
        listGrid.setEditEvent(ListGridEditEvent.CLICK);
        listGrid.setCanRemoveRecords(true);

        IButton filterButton = new IButton("Filter");
        filterButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.filterData(advancedFilter.getCriteria());
            }
        });

        IButton newButton = new IButton("Add New");
        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.startEditingNew();
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(advancedFilter);
        layout.addMember(filterButton);
        layout.addMember(listGrid);
        layout.addMember(newButton);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                    new SourceEntity("World.java", JAVA, "source/beans/World.java.html", true),
                    new SourceEntity("META-INF/persistence.xml", XML, "source/ds/common/persistence2.xml.html", true)
                };
    }
}
