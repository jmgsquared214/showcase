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
package com.smartgwt.sample.showcase.client.componentXML;

import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class ScreenReuse extends ShowcasePanel {

    private static final String DESCRIPTION =
        "Using <code>createScreen()</code>, you can load multiple copies of the same Component XML " + 
        "screen, and the copies will not interfere with each other.  This allows Component XML " +
        "screens to be treated as simple reusable components." +
        "<P>" + 
        "Use the \"DataSources\" drop-down below to select a DataSource.  Each time you select a new "+
        "DataSource, a new copy of the same Component XML screen is created, and its components are "+
        "bound to the selected DataSource.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            ScreenReuse panel = new ScreenReuse();
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

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    TabSet tabSet;
    
    public Canvas getViewPanel() {

    	
    	DynamicForm selectorForm = new DynamicForm();
    	selectorForm.setWidth("100%");
    	SelectItem selector = new SelectItem("selector");
    	selector.setValueMap("countryDS", "supplyItem");
    	selector.setTitle("Select a DataSource");
    	
    	selectorForm.setFields(selector);
    	selector.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				switchTo(event.getItem().getDisplayValue());
			}
		});
    	
    	tabSet = new TabSet();
    	
        final VLayout layout = new VLayout();
        layout.setWidth("100%");
        layout.setHeight("90%");
        layout.setMembersMargin(10);
        layout.setMembers(selectorForm, tabSet);
        
        RPCManager.cacheScreens(new String[]{"screenReuse"}, new Function() {

			@Override
			public void execute() {
				switchTo("countryDS");
			}
        });
        return layout;
    }

    private void switchTo(String dataSource)
    {
    	String tabId = "tabFor"+dataSource;
    	
    	// if no tab was created yet for this DataSource, create one
    	if (tabSet.getTab(tabId) == null)
    	{
    		// create a new Tab
	    	Tab tab = new Tab(dataSource);
	    	tab.setID(tabId);

	    	// create screen
	    	Canvas screen = RPCManager.createScreen("screenReuse");

	    	// get the components we want to use by their local ID
	    	final Button saveButton = (Button)screen.getByLocalId("saveButton");//new Button(screen.getByLocalId("saveButton").getJsObj());
	    	final DynamicForm editForm = (DynamicForm)screen.getByLocalId("editForm");//new DynamicForm(screen.getByLocalId("editForm").getJsObj());
	    	final ListGrid listGrid = (ListGrid)screen.getByLocalId("listGrid");//new ListGrid(screen.getByLocalId("listGrid").getJsObj());
	    	
	    	// set DataSource
	    	editForm.setDataSource(DataSource.get(dataSource));
	    	listGrid.setDataSource(DataSource.get(dataSource));
	    	
	    	// set handlers
	    	listGrid.addRecordClickHandler(new RecordClickHandler() {
				
				@Override
				public void onRecordClick(RecordClickEvent event) {
					editForm.clearErrors(true);
					editForm.editRecord(event.getRecord());
					saveButton.enable();
				}
			});
	    	
	    	saveButton.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					editForm.saveData();
				}
			});
	    	
	    	tab.setPane(screen);
	    	tabSet.addTab(tab);
	    	tabSet.selectTab(tabId);
    	}
    	else
    	{
    		tabSet.selectTab(tabId);
    	}
    }
    
    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("screenReuse.ui.xml", XML, "source/screenReuse.ui.xml.html", true),
            new SourceEntity("supplyItem.ds.xml", XML, "source/supplyItem.ds.xml.html", true),
            new SourceEntity("countryDS.ds.xml", XML, "source/countryDS.ds.xml.html", true)
        };
    }
}
