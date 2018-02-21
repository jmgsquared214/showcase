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

import java.util.Date;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class DynamicReportingSample extends ShowcasePanel {

    private static final String DESCRIPTION ="<p>This example shows the use of custom SQL clauses to build a fairly complex" +
            " query, including both standard " +
            "and bespoke WHERE conditions and the use of aggregate functions and a GROUP BY. It is important to note that" +
            " this can be done " +
            "whilst still keeping the normal benefits of Smart GWT DataSources, such as automatic dataset paging and" +
            " arbitrary filtering and sorting. " +
            "Also note that this example, though it makes heavy use of custom SQL clauses, is also portable across different" +
            " database products. </p>" +
            "<p>The list contains a summary of orders in a given date range, summarized by item - each item appears just once" +
            " in the list, alongside the total " +
            "quantity of that item ordered in the given date range. Change the date range to be more restrictive (all the" +
            " rows in the sample database have dates in February 2016) " +
            "and click \"Filter\", and see the quantities change, and items disappear from the list. Also," +
            " use the filter editor at the top of the grid " +
            "to arbitrarily filter the records, or click the column headings to sort.</p>" +
            "<p>Scroll the grid quickly to the bottom, and a brief notification as Smart GWT contacts the " +
            "server will be seen - pagination is still working, despite the unusual " +
            "and complex query.</p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the SQL Templating feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Enterprise Edition</a>.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#sql_dynamic_reporting\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return Showcase.hasSQLTemplating();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DynamicReportingSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {

        DataSource dataSource = DataSource.get("dynamicReporting_orderItem");
        
        final ListGrid orderItemSupplyList = new ListGrid();
        orderItemSupplyList.setWidth(700);
        orderItemSupplyList.setHeight(300);
        orderItemSupplyList.setAlternateRecordStyles(true);
        orderItemSupplyList.setDataSource(dataSource);
        orderItemSupplyList.setShowFilterEditor(true);

        //see the "summary" operation declared in the dynamicReporting_orderItem DataSource defintion
        orderItemSupplyList.setFetchOperation("summary");

        //deliberately small, to show server-side paging and filtering
        orderItemSupplyList.setDataPageSize(15);

        //disable this performance feature, again to force server visits
        orderItemSupplyList.setDrawAllMaxCells(0);

        ListGridField itemNameField = new ListGridField("itemID", "Item Name");
        itemNameField.setDisplayField("itemName");
        itemNameField.setWidth("50%");

        TextItem textItemEditor = new TextItem();
        textItemEditor.setFetchMissingValues(false);
        itemNameField.setFilterEditorType(textItemEditor);

        ListGridField skuField = new ListGridField("SKU");
        ListGridField unitCostField = new ListGridField("unitCost");
        unitCostField.setWidth(150);
        ListGridField quantityField = new ListGridField("quantity");

        ListGridField totalSalesField = new ListGridField("totalSales");
        totalSalesField.setCanFilter(false);
        totalSalesField.setWidth(100);
        totalSalesField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if(value == null) return null;
                return String.valueOf(Math.round(((Number)value).floatValue() *100) / 100);
            }
        });

        orderItemSupplyList.setFields(itemNameField, skuField, unitCostField, quantityField, totalSalesField);

        //create form
        final DynamicForm orderItemCriteriaForm = new DynamicForm();
        orderItemCriteriaForm.setNumCols(2);
        orderItemCriteriaForm.setWidth(400);

        DateItem startDate = new DateItem();
        startDate.setName("startDate");
        startDate.setTitle("Start&nbsp;Date");
        startDate.setDefaultValue(new Date(116, 1, 1));
        startDate.setUseTextField(false);
        
        DateItem endDate = new DateItem();
        endDate.setName("endDate");
        endDate.setTitle("End&nbsp;Date");
        endDate.setDefaultValue(new Date(117, 2, 1));
        endDate.setUseTextField(false);

        ButtonItem filterItem = new ButtonItem("filterButton", "Filter");
        filterItem.setEndRow(false);
        filterItem.setStartRow(false);
        filterItem.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Criteria criteria = orderItemSupplyList.getFilterEditorCriteria(true);
                if(criteria == null) criteria = new Criteria();
                
                Criteria formCriteria = orderItemCriteriaForm.getValuesAsCriteria();
                criteria.addCriteria(formCriteria);

                orderItemSupplyList.invalidateCache();
                orderItemSupplyList.filterData(criteria);
            }
        });

        orderItemCriteriaForm.setFields(startDate, endDate, filterItem);

        VLayout layout = new VLayout(20);
        layout.addMember(orderItemCriteriaForm);
        layout.addMember(orderItemSupplyList);

        IButton exportButton = new IButton("Export Data");
        exportButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                DSRequest dsRequestProperties = new DSRequest();
                dsRequestProperties.setOperationId("summary");
                orderItemSupplyList.exportData(dsRequestProperties);
            }
        });

        layout.addMember(exportButton);

        orderItemSupplyList.fetchData(orderItemCriteriaForm.getValuesAsCriteria());

        return layout;
    }


    public String getIntro() {
        return DESCRIPTION;
    }
}
