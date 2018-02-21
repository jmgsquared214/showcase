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

package com.smartgwt.sample.showcase.client.dataintegration.java.others;

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.LogicalDate;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.BooleanItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class FormattedExportSample extends ShowcasePanel {
    private static final String DESCRIPTION =
        "Client-side data can be exported from a DataBoundComponent. That is, the data as "+
        "seen in a component, including the effects of client-side formatters (including custom formatters) and hilites, can be exported. " +
        "Data exported this way appears in the spreadsheet as formatted text. In the particular " +
        "case of dates, this behavior can be overridden for each export, because exporting formatted " +
        "dates can be undesirable - if the value is exported as a formatted string, Excel is not " +
        "able to recognize it as a date.<p>" +
        "Custom formatting for dates is often not necessary, because declared FormatStrings are flexible " +
        "enough to handle the majority of formatting requirements. The example below shows a (rather " +
        "contrived) case where custom formatters really are required." +
        "<p>In the example, choose an export format from the select-list, decide whether " + 
        "to download the results or view them in a window using the checkbox and click the " +
        "\"Export\" button." +
        "<p>Data is exported according to the filters and sort-order on the grid and includes " +
        "the formatted values and field-titles as seen in the grid.   Depending on how you set " +
        "the \"export dates as formatted strings\" checkbox, you will either see dates with some " +
        "default format, or the actual strings shown in the ListGrid. ";
    
    private static final long MS_PER_DAY = 24 * 60 * 60 * 1000;

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the Client Export feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Power Edition</a> or better.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#formatted_export\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return Showcase.hasClientExport();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new FormattedExportSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final DataSource dataSource = DataSource.get("largeValueMap_orderItem");

        final ListGrid orderListGrid = new ListGrid();
        orderListGrid.setWidth100();
        orderListGrid.setAlternateRecordStyles(true);
        orderListGrid.setDataSource(dataSource);
        orderListGrid.setAutoFetchData(true);

        ListGridField orderIdField = new ListGridField("orderID");
        orderIdField.setWidth(90);
        ListGridField itemIdField = new ListGridField("itemID", "Item Name");
        itemIdField.setWidth("*");
        itemIdField.setDisplayField("itemName");
        
        ListGridField quantityField = new ListGridField("quantity","Qty");
        quantityField.setWidth(50);
        ListGridField unitPriceField = new ListGridField("unitPrice");
        unitPriceField.setAlign(Alignment.RIGHT);
        unitPriceField.setWidth(100);
        ListGridField orderDateField = new ListGridField("orderDate");
        orderDateField.setWidth(144);
        orderDateField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return (String) value;
                if (value.getClass() == com.smartgwt.client.util.LogicalDate.class) {
                    LogicalDate dateValue = (LogicalDate) value;
                    long days = (long)(new Date().getTime() - dateValue.getTime()) / MS_PER_DAY;
                    return days + " days ago";
                }
                return value.toString();
            }
        });
        
        ListGridField itemTotalField = new ListGridField("itemTotalField", "Item Total");
        itemTotalField.setWidth(100);
        itemTotalField.setAlign(Alignment.RIGHT);
        itemTotalField.setCellAlign(Alignment.RIGHT);
        final NumberFormat numFormatter = NumberFormat.getFormat("###,###,###.00");
        itemTotalField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
            	double quantity=record.getAttributeAsDouble("quantity");
            	double price=record.getAttributeAsDouble("unitPrice");
                return numFormatter.format(quantity*price);
            }
        });
        
        orderListGrid.setFields(orderIdField, itemIdField, quantityField, unitPriceField,
        		orderDateField, itemTotalField);
        orderListGrid.setShowFilterEditor(true);

        final DynamicForm exportForm = new DynamicForm();
        
        exportForm.setWidth(300);

        SelectItem exportTypeItem = new SelectItem("exportType", "Export Type");
        exportTypeItem.setWidth("*");
        exportTypeItem.setDefaultToFirstOption(true);

        LinkedHashMap valueMap = new LinkedHashMap();
        valueMap.put("csv", "CSV (Excel)");
        valueMap.put("xml", "XML");
        valueMap.put("json", "JSON");
        valueMap.put("xls", "XLS (Excel97)");
        valueMap.put("ooxml", "XLSX (Excel2007/OOXML)");

        exportTypeItem.setValueMap(valueMap);

        BooleanItem exportDatesItem = new BooleanItem();
        exportDatesItem.setName("exportDatesFormatted");
        exportDatesItem.setTitle("Export dates as formatted strings");
        exportDatesItem.setAlign(Alignment.LEFT);

        BooleanItem showInWindowItem = new BooleanItem();
        showInWindowItem.setName("showInWindow");
        showInWindowItem.setTitle("Show in Window");
        showInWindowItem.setAlign(Alignment.LEFT);
        showInWindowItem.setVisible(!Showcase.isIOs());

        exportForm.setItems(exportTypeItem, exportDatesItem, showInWindowItem);

        IButton exportButton = new IButton("Export");
        exportButton.addClickHandler(new ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                String exportAs = (String) exportForm.getField("exportType").getValue();

                FormItem item = exportForm.getField("showInWindow");
                boolean showInWindow =  item.getValue() == null ? false : (Boolean) item.getValue();
                item = exportForm.getField("exportDatesFormatted");
                boolean formatted =  item.getValue() == null ? false : (Boolean) item.getValue();

                if(exportAs.equals("json")) {
                    // JSON exports are server-side only, so use the OperationBinding on the DataSource
                    DSRequest dsRequestProperties = new DSRequest();
                    dsRequestProperties.setOperationId("customJSONExport");
                    dsRequestProperties.setExportDisplay(showInWindow ? ExportDisplay.WINDOW : ExportDisplay.DOWNLOAD);
                    dsRequestProperties.setExportDatesAsFormattedString(formatted);

                    orderListGrid.exportClientData(dsRequestProperties);
                } else {
                    // exportAs is either XML or CSV, which we can do with requestProperties
                    DSRequest dsRequestProperties = new DSRequest();
                    dsRequestProperties.setExportAs((ExportFormat)EnumUtil.getEnum(ExportFormat.values(), exportAs));
                    dsRequestProperties.setExportDisplay(showInWindow ? ExportDisplay.WINDOW : ExportDisplay.DOWNLOAD);
                    dsRequestProperties.setExportDatesAsFormattedString(formatted);

                    orderListGrid.exportClientData(dsRequestProperties);
                }
            }
        });
    	
        VLayout layout = new VLayout(15);
        layout.setHeight100();

        HLayout formLayout = new HLayout(15);
        formLayout.setAutoHeight();
        formLayout.addMember(exportForm);
        formLayout.addMember(exportButton);
        layout.addMember(formLayout);

        layout.addMember(orderListGrid);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}