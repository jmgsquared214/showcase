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

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class UploadSample extends ShowcasePanel {

    private static final String DESCRIPTION =
    
    "<p>This example uses a DynamicForm bound to a DataSource with a field of type "+
    "<code>upload</code> to enable files to be uploaded and both a ListGrid and TileGrid to display "+
    "the existing records, via a shared ResultSet." +
    "<p>Enter a Title and select a local image-file to upload and click \"Save\" to upload the "+
    "file. Note that the file-size is limited to 50k via the DataSourceField property "+
    "<code>maxFileSize</code> (see the mediaLibrary tab from the View Source button)."+
    "<p>The image is stored in the DataSource as type <code>imageFile</code> and is automatically " +
    "rendered for databound viewers, such as the TileGrid and ListGrid below. " +
    "The value of <code>field.showFileInline</code> controls whether the image is displayed directly, " +
    "or whether links are provided for view and download.";

    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            UploadSample panel = new UploadSample();
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
        DataSource dataSource = DataSource.get("mediaLibrary");

        final DynamicForm uploadForm = new DynamicForm();
        uploadForm.setDataSource(dataSource);
        uploadForm.setWidth(300);
        
        TextItem uploadTitleItem = new TextItem("title");
        uploadTitleItem.setRequired(true);
        
        FileItem imageItem = new FileItem("image");
        imageItem.setHint("Maximum file-size is 50k");
        imageItem.setRequired(true);
        
        ButtonItem saveItem = new ButtonItem("save", "Save");
        saveItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                uploadForm.saveData(new com.smartgwt.client.data.DSCallback() {
                    public void execute(DSResponse response, Object data, DSRequest request) {
                        if (response.getStatus() >= 0 ) uploadForm.editNewRecord(); 
                    }
                });
            }
        });

        uploadForm.setFields(uploadTitleItem, imageItem, saveItem);

        final TileGrid mediaTileGrid = new TileGrid();
        final ListGrid mediaListGrid = new ListGrid();


        final DynamicForm searchForm = new DynamicForm();
        searchForm.setWidth("100%");
        searchForm.setNumCols(3);
        searchForm.setColWidths(60, 200, "*");
        searchForm.setSaveOnEnter(true);
        
        TextItem searchTitleItem = new TextItem("title", "Title");
        
        ButtonItem searchSearchItem = new ButtonItem("search", "Search");
        searchSearchItem.setStartRow(false);
        searchSearchItem.setEndRow(false);
        searchSearchItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
            	DSRequest req = new DSRequest();
            	req.setTextMatchStyle(TextMatchStyle.SUBSTRING);
                mediaTileGrid.fetchData(searchForm.getValuesAsCriteria(), null, req);
            }
        });
        
        searchForm.setFields(searchTitleItem, searchSearchItem);
        

        IButton viewAsTiles = new IButton("View as Tiles");
        viewAsTiles.setAutoFit(true);
        viewAsTiles.setIcon("silk/application_view_tile.png");
        viewAsTiles.addToRadioGroup("views");
        viewAsTiles.setSelected(true);
        viewAsTiles.setActionType(SelectionType.CHECKBOX);
        viewAsTiles.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
            	mediaTileGrid.show();
            	mediaListGrid.hide();
            }
        });
        
        IButton viewAsList = new IButton("View as List");
        viewAsList.setAutoFit(true);
        viewAsList.setIcon("silk/application_view_detail.png");
        viewAsList.addToRadioGroup("views");
        viewAsList.setActionType(SelectionType.CHECKBOX);
        
        final UploadSample thisSample = this;
        viewAsList.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                if (thisSample.firstTimeViewingListGrid) {
                	thisSample.firstTimeViewingListGrid = false;
                    mediaListGrid.setData(mediaTileGrid.getResultSet());
                }
            	mediaTileGrid.hide();
            	mediaListGrid.show();
            }
        });
        
        
        HLayout buttons = new HLayout(5);
        buttons.setWidth(300);
        buttons.setPadding(5);
        buttons.setMembers(viewAsTiles, viewAsList);
        
        mediaTileGrid.setWidth("100%");
        mediaTileGrid.setHeight(224);
        mediaTileGrid.setTileWidth(100);
        mediaTileGrid.setTileHeight(150);
        mediaTileGrid.setDataSource(dataSource);
        mediaTileGrid.setAutoFetchData(true);
        
        mediaListGrid.setWidth("100%");
        mediaListGrid.setHeight(224);
        mediaListGrid.setAlternateRecordStyles(true);
        mediaListGrid.setDataSource(dataSource);
        mediaListGrid.setVisibility(Visibility.HIDDEN);
        
        VLayout resultsLayout = new VLayout();
        resultsLayout.setLeft(350);
        resultsLayout.setWidth(500);
        resultsLayout.setHeight(250);
        resultsLayout.setMembers(searchForm, buttons, mediaTileGrid, mediaListGrid);
        
        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setWidth(50);

        HLayout topLayout = new HLayout();
        topLayout.setMembers(uploadForm, spacer, resultsLayout);
        
        return topLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
    
    boolean firstTimeViewingListGrid = true;
}