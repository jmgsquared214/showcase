package com.smartgwt.sample.showcase.client.dataintegration.java.file;
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



import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.MultiFileItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class MultiUploadSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>This example shows the behavior of a multiple file upload widget.</p>" +
        "<p>When using this, implement cascading record deletion server-side, "+
        "either with the SQL schema for the table, or with DMI.</p>";

    private DataSource          multiUploadMaster;
    private DataSource          multiUploadDetail;
    private ListGrid            listGrid;

    private DynamicForm dynamicForm;

    public String getIntro() {
        return DESCRIPTION;
    }

    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            MultiUploadSample panel = new MultiUploadSample();
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

    public Canvas getViewPanel() {
        
        multiUploadMaster = DataSource.get("multiUploadMaster");
        multiUploadDetail = DataSource.get("multiUploadDetail");
        
        listGrid = new ListGrid();
        listGrid.setDataSource(multiUploadMaster);
        listGrid.setWidth(500);
        listGrid.setHeight(200);
        listGrid.setShowRollOver(false);
        listGrid.setAutoFetchData(true);
        listGrid.addRecordClickHandler(new RecordClickHandler() {
            @Override
            public void onRecordClick(RecordClickEvent event) {
                dynamicForm.editRecord(event.getRecord());
            }
        });

        MultiFileItem multiFilePicker = new MultiFileItem("file"); 
        multiFilePicker.setAttribute("dataSource", multiUploadDetail.getID());
        
        dynamicForm = new DynamicForm(); 
        dynamicForm.setWidth(500);
        dynamicForm.setDataSource(multiUploadMaster);
        dynamicForm.setFields(new TextItem("title"),multiFilePicker);
  
        Button saveButton = new Button("Save Form");
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dynamicForm.saveData();
            }
        });

        Button removeButton = new Button("Delete Row");
        removeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listGrid.removeSelectedData();
                dynamicForm.editNewRecord();
            }
        });
        Button cleareButton = new Button("Clear Form");
        cleareButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dynamicForm.editNewRecord();
            }
        });

        VLayout topLayout = new VLayout();
        topLayout.setMembersMargin(10);
        topLayout.addMember(listGrid);
        topLayout.addMember(removeButton);
        topLayout.addMember(dynamicForm);
        
        HLayout buttonLayout = new HLayout();
        buttonLayout.setMembersMargin(10);
        buttonLayout.addMember(saveButton);
        buttonLayout.addMember(cleareButton);
        
        topLayout.addMember(buttonLayout);

        return topLayout;
    }
    
}