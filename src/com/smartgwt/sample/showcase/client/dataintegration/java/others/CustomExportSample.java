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

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.BooleanItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class CustomExportSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "<p>A formatted export can be produced using DMI (Direct Method Invocation) and affecting data server-side. " +
        "This example shows a normal export via a DMI in an <code>operationBinding</code>, where the DMI " +
        "enhances the exported data, formatting the \"Independence date\" field and adding a " +
        "calculated field \"gdppercapita\" on the server-side." +
        "<p>Choose an Export-Format from the select-list, decide whether to download the " +
        "results or view them in a window using the checkbox and click the \"Export\" button. " +
        "In this case, exporting to all formats is achieved via <code>operationBindings</code> that " +
        "specify the server DMI and, in the case of exports to JSON, also the <code>exportAs</code> flag. "+
        "See the Source and worldDSExportCustom tabs for details.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            CustomExportSample panel = new CustomExportSample();
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

        final DataSource worldDSExportCustom = DataSource.get("worldDSExportCustom");
        
        final ListGrid countryList = new ListGrid();
        countryList.setWidth(600);
        countryList.setAlternateRecordStyles(true);
        countryList.setDataSource(worldDSExportCustom);
        countryList.setAutoFetchData(true);

        ListGridField countryName = new ListGridField("countryName", "Country");
        ListGridField capital = new ListGridField("capital", "Capital");
        ListGridField continent = new ListGridField("continent", "Continent");
        ListGridField independence = new ListGridField("independence", "Nationhood", 120);
        ListGridField population = new ListGridField("population", "Population");

        countryList.setFields(countryName, capital, continent, independence, population);
        countryList.setAutoFitData(Autofit.VERTICAL);
        countryList.setShowFilterEditor(true);
        countryList.setAutoFitMaxRecords(10);
        
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

        BooleanItem showInWindowItem = new BooleanItem();
        showInWindowItem.setName("showInWindow");
        showInWindowItem.setTitle("Show in Window");
        showInWindowItem.setAlign(Alignment.LEFT);
        showInWindowItem.setVisible(!Showcase.isIOs());

        exportForm.setItems(exportTypeItem, showInWindowItem);

        IButton exportButton = new IButton("Export");
        exportButton.addClickHandler(new ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                String exportAs = (String) exportForm.getField("exportType").getValue();

                FormItem item = exportForm.getField("showInWindow");
                boolean showInWindow =  item.getValue() == null ? false : (Boolean) item.getValue();

                if(exportAs.equals("json")) {
                    // JSON exports are server-side only, so use the OperationBinding on the DataSource
                    DSRequest dsRequestProperties = new DSRequest();
                    dsRequestProperties.setOperationId("customJSONExport");
                    dsRequestProperties.setExportDisplay(showInWindow ? ExportDisplay.WINDOW : ExportDisplay.DOWNLOAD);

                    countryList.exportData(dsRequestProperties);
                } else {
                   // exportAs is either XML or CSV, which we can do with requestProperties
                    DSRequest dsRequestProperties = new DSRequest();
                    dsRequestProperties.setOperationId("customExport");
                    dsRequestProperties.setExportAs((ExportFormat)EnumUtil.getEnum(ExportFormat.values(), exportAs));
                    dsRequestProperties.setExportDisplay(showInWindow ? ExportDisplay.WINDOW : ExportDisplay.DOWNLOAD);

                    countryList.exportData(dsRequestProperties);
                }
            }
        });

        VLayout layout = new VLayout(15);
        layout.setAutoHeight();

        HLayout formLayout = new HLayout(15);
        formLayout.addMember(exportForm);
        formLayout.addMember(exportButton);
        layout.addMember(formLayout);

        layout.addMember(countryList);

        return layout;

    }

    public String getIntro() {
        return DESCRIPTION;
    }
    
    public SourceEntity[] getSourceUrls() {
            return new SourceEntity[]{
            new SourceEntity("server/CustomExportDMI.java", JAVA, "source/datasource/CustomExportDMI.java.html", true)
            };
    }

}