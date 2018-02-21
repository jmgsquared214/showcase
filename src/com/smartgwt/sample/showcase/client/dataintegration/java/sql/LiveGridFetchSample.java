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

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class LiveGridFetchSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>Rows are fetched from the server automatically as the user drags the scrollbar. Double Click a record to" +
            " edit it. Drag the scrollbar quickly to the bottom to fetch a range near the end. </p>" +
            "<p>Scroll slowly back up to fill in the middle.</p>" +
            "<p>Another key unique feature of Smart GWT is lazy rendering of columns. Most browsers cannot handle" +
            " displaying a large number of columns and have serious performance issues. Smart GWT however does not render all" +
            " columns outside the visible area by default and only renders them as horizontal scrolling occurs. However, this" +
            " feature can be disabled if desired.</p>" +
            "<p>It is also possible to control how far ahead of the currently visible area rows should be rendered. This is expressed as a " +
            "ratio from viewport size to rendered area size. The default is 1.3.</p>" +
            "<p>Tweaking <code>drawAheadRatio</code> allows for tradeoffs between continuous scrolling speed vs initial render " +
            "time and render time when scrolling by large amounts.</p>";

    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            LiveGridFetchSample panel = new LiveGridFetchSample();
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
        DataSource dataSource = DataSource.get("supplyItem");

        ListGridField itemName = new ListGridField("itemName", 220);
        ListGridField sku = new ListGridField("SKU", 100);
        ListGridField description = new ListGridField("description", 190);
        ListGridField category = new ListGridField("category", 190);
        ListGridField units = new ListGridField("units", 50);

        ListGridField unitCost = new ListGridField("unitCost", 100);
        unitCost.setType(ListGridFieldType.FLOAT);

        ListGridField inStock = new ListGridField("inStock", 100);
        inStock.setType(ListGridFieldType.BOOLEAN);

        ListGridField nextShipment = new ListGridField("nextShipment", 100);
        nextShipment.setType(ListGridFieldType.DATE);


        final ListGrid listGrid = new ListGrid();
        listGrid.setCanEdit(true);
        listGrid.setWidth(900);
        listGrid.setHeight100();
        listGrid.setAutoFetchData(true);
        listGrid.setDataSource(dataSource);
        listGrid.setEditEvent(ListGridEditEvent.DOUBLECLICK);
        listGrid.setModalEditing(true);
        listGrid.setShowRowNumbers(true);

        listGrid.setFields(itemName, sku, description, category, units, unitCost);

        return listGrid;
    }


    public String getIntro() {
        return DESCRIPTION;
    }

}