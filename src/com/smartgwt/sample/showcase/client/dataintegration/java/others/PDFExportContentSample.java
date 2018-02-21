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

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class PDFExportContentSample extends ShowcasePanel {
private static final String DESCRIPTION = "Smart GWT provides comprehensive support for rendering UI into a print-friendly "+
        "fashion, and exporting to PDF. "+
        "<p>"+        
        "Click the \"Print Preview\" button and note the following things: "+
        "<ul>"+
        "<li>All components have simplified appearance (eg gradients omitted) to be legible in "+ 
        "black and white</li> "+
        "<li>The ListGrid had a scrollbar because it wasn't big enough to show all records, "+ 
        "but the printable view shows all data</li> "+
        "<li>Buttons and other interactive controls that are not meaningful in print view are omitted</li> "+
        "</ul>"+
        "Click the \"Export\" button to download a .pdf of the printed view.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            PDFExportContentSample panel = new PDFExportContentSample();
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
        ListGrid countryList = new ListGrid();
        countryList.setWidth(500);
        countryList.setHeight(350);
        countryList.setAlternateRecordStyles(true);
        countryList.setCanExpandRecords(true);
        countryList.setExpansionMode(ExpansionMode.DETAIL_FIELD);
        countryList.setDetailField("background");
        countryList.setData(CountrySampleData.getNewRecords());
        ListGridField countryNameField = new ListGridField("countryName", "Country");
        ListGridField capitalField = new ListGridField("capital", "Capital");
        ListGridField continentField = new ListGridField("continent", "Continent");
        countryList.setFields(countryNameField, capitalField, continentField);
        countryList.expandRecord(countryList.getRecord(2));
        countryList.expandRecord(countryList.getRecord(4));
		
        final VLayout mainLayout = new VLayout();
        mainLayout.setMembersMargin(5);

        IButton buttonPdf = new IButton();
        buttonPdf.setWidth(150);
        buttonPdf.setTitle("Export");
        buttonPdf.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                DSRequest requestProperties = new DSRequest();
                requestProperties.setAttribute("skinName", "Enterprise");
                requestProperties.setAttribute("pdfName", "export");
                RPCManager.exportContent(mainLayout, requestProperties);
            }
        });
        IButton buttonPreview = new IButton();
        buttonPreview.setWidth(150);
        buttonPreview.setTitle("Show Print Preview");
        buttonPreview.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Canvas.showPrintPreview(mainLayout);
            }
        });
        HLayout hLayout = new HLayout();
        hLayout.setMembersMargin(5);
        hLayout.setMembers(buttonPdf, buttonPreview);

        mainLayout.addMembers(countryList, hLayout);

        return mainLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("CountrySampleData.java", JAVA, "source/dataintegration/java/others/CountrySampleData.java.html", false)
        };
    }
}
