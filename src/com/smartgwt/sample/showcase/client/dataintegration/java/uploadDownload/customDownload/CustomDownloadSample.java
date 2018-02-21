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

package com.smartgwt.sample.showcase.client.dataintegration.java.uploadDownload.customDownload;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class CustomDownloadSample extends ShowcasePanel {
    private static final String DESCRIPTION = "This sample shows a custom download in which server-side Java logic generates data to be "+
                        "downloaded to the user.  This pattern can be used to download a file that is derived from "+
                        "DataSource data, such as exporting DataSource data in a custom text format, or via "+
                        "third-party libraries that can produce some kind of structured file (such as an .rtf or "+
                        ".pdf file). "+
                        "<P>"+
                        "Select multiple records in the grid below and press the \"Download Descriptions\" button. "+
                        "This will produce a simple text file with descriptions for all of the selected records.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            CustomDownloadSample panel = new CustomDownloadSample();
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
        final DataSource ds = DataSource.get("supplyItemDownload");
        Criteria criteria = new Criteria();
        // This criteria is only applied in order to avoid showing items with empty descriptions.
        criteria.addCriteria("description", "and");

        final ListGrid listGridSuppyItem = new ListGrid();
        listGridSuppyItem.setWidth(900);
        listGridSuppyItem.setHeight(224);
        listGridSuppyItem.setDataSource(ds);
        listGridSuppyItem.setAlternateRecordStyles(true);
        listGridSuppyItem.setSelectionType(SelectionStyle.SIMPLE);
        listGridSuppyItem.filterData(criteria);
        
        final DynamicForm formDownload = new DynamicForm();
        CheckboxItem checkbox = new CheckboxItem();
        checkbox.setTitle("Download to new window");
        checkbox.setName("checkbox");
        formDownload.setFields(checkbox);
        
        IButton buttonDownloadDescriptions = new IButton();
        buttonDownloadDescriptions.setTitle("Download Descriptions");
        buttonDownloadDescriptions.setWidth(200);
        buttonDownloadDescriptions.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ListGridRecord[] records = listGridSuppyItem.getSelectedRecords();
                if (records.length == 0) {
                    SC.say("You must select at least one record");
                    return;
                }
                Integer[] arrayItemIDs = new Integer[records.length];
                for (int i = 0; i < records.length; i++) {
                    arrayItemIDs[i] = records[i].getAttributeAsInt("itemID");
                }
                Criteria criteria = new Criteria();
                criteria.addCriteria("itemID", arrayItemIDs);
			
                DSRequest dsRequest = new DSRequest();
                dsRequest.setOperationId("downloadDescriptions");
                dsRequest.setDownloadResult(true);
                if (formDownload.getValue("checkbox") != null) {
                    if (Boolean.parseBoolean(formDownload.getValueAsString("checkbox")) == true) {
                        dsRequest.setDownloadToNewWindow(true);
                    } else {
                        dsRequest.setDownloadToNewWindow(false);
                    }
                } else {
                    dsRequest.setDownloadToNewWindow(false);
                }
                ds.fetchData(criteria, null, dsRequest);
            }
        });
        HLayout hLayout = new HLayout();
        hLayout.setWidth(300);
        hLayout.addMember(buttonDownloadDescriptions);
        hLayout.addMember(formDownload);
        
        VLayout vLayout = new VLayout();
        vLayout.setWidth(300);
        vLayout.addMember(listGridSuppyItem);
        vLayout.addMember(hLayout);
        
        return vLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
    
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("CustomDownload.java", JAVA, "source/dataintegration/java/uploadDownload/customDownload/CustomDownload.java.html", true)
        };
    }
}