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
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class CustomExportCustomResponseSample extends ShowcasePanel {
private static final String DESCRIPTION = "Entirely custom data can be exported via a DMI (Direct Method Invocation). Click the button to issue a call to dataSource.exportData() with an "+
                "operationId that specifies a server DMI. In this example, the DMI method ignores all the regular export parameters, calls doCustomResponse() and "+
                "writes directly into the response output stream. ";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            CustomExportCustomResponseSample panel = new CustomExportCustomResponseSample();
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
        final DataSource ds = DataSource.get("supplyItemExport");
        IButton exportButton = new IButton();
        exportButton.setTitle("Do Custom Export");
        exportButton.setAutoFit(true);
        exportButton.setLeft(20);
        exportButton.setTop(20);
        exportButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                DSRequest requestProperties = new DSRequest();
                requestProperties.setOperationId("customExport");
                ds.exportData(null, requestProperties);
            }
        });

        return exportButton;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
    
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("CustomExportCustomResponseDMI.java", JAVA, "source/dataintegration/java/others/CustomExportCustomResponseDMI.java.html", true)
        };
    }
}