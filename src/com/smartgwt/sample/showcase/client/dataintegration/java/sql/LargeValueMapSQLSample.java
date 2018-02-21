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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class LargeValueMapSQLSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>This example shows the simple use of custom SQL clauses to provide a DataSource that joins multiple tables" +
            " while retaining Smart GWT's automatic paging and filtering behavior. When trying this example, remember that this" +
            " is <b>automatic</b> dataset-handling behavior that works without any coding, even though the data is being" +
            " provided by a custom SQL query.</p>" +
            "<p>The list contains order items; each order item holds an \"itemID\", which is being used to join to the supplyItem " +
            "table to obtain the \"itemName\". Note that you can filter on the \"itemName\". Select either a full item name or just" +
            " enter a partial value in the combo box. " +
            "Pagination is also active - try quickly dragging the scrollbar down, and see Smart GWT contacting the " +
            "server for more records.</p>" +
            "<p>Editing is also enabled in this example. Try filtering to a small sample of items, then edit one of them by " +
            "double-clicking it and choose a different item. Note how that order item is immediately filtered out of the " +
            "list: Smart GWT's intelligent cache sync also automatically handles custom SQL statements.</p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the SQL Templating feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Enterprise Edition</a>.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#large_valuemap_sql\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return Showcase.hasSQLTemplating();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new LargeValueMapSQLSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        DataSource dataSource = DataSource.get("largeValueMap_orderItem");
        final DataSource feds = DataSource.get("supplyItem");

        final ListGrid orderListGrid = new ListGrid();
        orderListGrid.setWidth(550);
        orderListGrid.setHeight(224);
        orderListGrid.setDataSource(dataSource);
        orderListGrid.setAutoFetchData(true);
        orderListGrid.setShowFilterEditor(true);
        orderListGrid.setCanEdit(true);

        ListGridField orderIdField = new ListGridField("orderID");
        ListGridField itemIdField = new ListGridField("itemID", "Item Name");
        itemIdField.setWidth("50%");
        itemIdField.setEditorProperties(new SelectItem());
        itemIdField.setFilterEditorType(new ComboBoxItem());
        itemIdField.setDisplayField("itemName");
        
        FormItem feProps = new FormItem() {{ setOptionDataSource(feds); }};
        itemIdField.setFilterEditorProperties(feProps);

        ListGridField quantityField = new ListGridField("quantity");
        ListGridField unitPriceField = new ListGridField("unitPrice");

        orderListGrid.setFields(orderIdField, itemIdField, quantityField, unitPriceField);

        return orderListGrid;
    }


    public String getIntro() {
        return DESCRIPTION;
    }
}
