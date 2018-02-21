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

package com.smartgwt.sample.showcase.client.dataintegration.java.uploadDownload;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class CustomBinaryFieldSample extends ShowcasePanel {
private static final String DESCRIPTION = "Support for binary fields is built-in to the framework, including saving binary field values to SQL, Hibernate or JPA, "+
                        "for downloading later. "+
                        "<P>"+
                        "This example shows how to implement a binary field if a Custom DataSource were being built, that does not use the built-in support for "+
                        "binary field persistence. "+
                        "<P>"+
                        "UI controls with built-in support for binary fields will then show controls for uploading and downloading files, which will work with any custom DataSource. ";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            CustomBinaryFieldSample panel = new CustomBinaryFieldSample();
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
        final DataSource ds = DataSource.get("customBinaryField");

        final DynamicForm formView = new DynamicForm();
        formView.setWidth("100%");
        formView.setDataSource(ds);

        TextItem idViewItem = new TextItem();
        idViewItem.setName("id");
        idViewItem.setWidth(150);
        idViewItem.setCanEdit(false);
        FileItem fileViewItem = new FileItem();
        fileViewItem.setName("file");
        fileViewItem.setCanEdit(false);
        formView.setFields(idViewItem, fileViewItem);

        final DynamicForm formEdit = new DynamicForm();
        formEdit.setWidth(100);
        formEdit.setDataSource(ds);
        TextItem idEditItem = new TextItem();
        idEditItem.setName("id");
        idEditItem.setWidth(150);
        idEditItem.setRequired(true);
        FileItem fileEditItem = new FileItem();
        fileEditItem.setName("file");
        fileEditItem.setRequired(true);
        formEdit.setFields(idEditItem, fileEditItem);

        IButton uploadButton = new IButton();
        uploadButton.setTitle("Upload");
        uploadButton.setWidth(100);
        uploadButton.addClickHandler(new ClickHandler() {
            @Override
                public void onClick(ClickEvent event) {
                    formEdit.saveData(new DSCallback() {
                        @Override
                        public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                            if (dsResponse.getStatus() >= 0) formEdit.editNewRecord();
                        }
                    });
                }
        });

        Label labelEdit = new Label();
        labelEdit.setContents("Editor");
        labelEdit.setWidth(50);
        labelEdit.setHeight(25);
        labelEdit.setBaseStyle("exampleSeparator");
        Label labelView = new Label();
        labelView.setContents("View");
        labelView.setWidth(50);
        labelView.setHeight(25);
        labelView.setBaseStyle("exampleSeparator");

        VLayout vLayoutForms = new VLayout();
        vLayoutForms.setWidth(100);
        vLayoutForms.addMembers(labelEdit,formEdit,uploadButton,labelView,formView);

        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth(500);
        listGrid.setHeight(224);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setDataSource(ds);
        listGrid.setSelectionType(SelectionStyle.SINGLE);
        listGrid.setAutoFetchData(true);
        ListGridField idField = new ListGridField("id");
        idField.setWidth(100);
        ListGridField fileField = new ListGridField("file");
        fileField.setWidth(380);
        listGrid.setFields(idField,fileField);
        listGrid.addRecordClickHandler(new RecordClickHandler() {
            @Override
            public void onRecordClick(RecordClickEvent event) {
                formEdit.editSelectedData(listGrid);
                formView.editSelectedData(listGrid);
            }
        });
        HLayout hLayoutTop = new HLayout();
        hLayoutTop.setWidth(700);
        hLayoutTop.setLayoutMargin(10);
        hLayoutTop.setMembersMargin(10);
        hLayoutTop.addMembers(listGrid,vLayoutForms);

        IButton buttonDownload = new IButton();
        buttonDownload.setWidth(200);
        buttonDownload.setTitle("Download Selected File");
        buttonDownload.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ListGridRecord selectedRecord = listGrid.getSelectedRecord();
                if (selectedRecord == null) {
                    SC.warn("You must select one record");
                    return;
                }
                ds.downloadFile(selectedRecord);
            }
        });
        IButton buttonView = new IButton();
        buttonView.setWidth(200);
        buttonView.setTitle("View Selected File");
        buttonView.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ListGridRecord selectedRecord = listGrid.getSelectedRecord();
                if (selectedRecord == null) {
                    SC.warn("You must select one record");
                    return;
                }
                ds.viewFile(selectedRecord);
            }
        });

        HLayout hLayoutButtons = new HLayout();
        hLayoutButtons.setWidth(500);
        hLayoutButtons.setLayoutMargin(10);
        hLayoutButtons.setMembersMargin(10);
        hLayoutButtons.addMembers(buttonDownload, buttonView);

        VLayout vLayout = new VLayout();
        vLayout.setWidth(300);
        vLayout.addMembers(hLayoutTop, hLayoutButtons);

        return vLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
    
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("CustomBinaryFieldDataSource.java", JAVA, "source/dataintegration/java/uploadDownload/CustomBinaryFieldDataSource.java.html", true)
        };
    }
}