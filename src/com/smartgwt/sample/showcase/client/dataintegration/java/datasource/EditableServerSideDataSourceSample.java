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

package com.smartgwt.sample.showcase.client.dataintegration.java.datasource;

import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.rpc.DMI;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

import com.smartgwt.client.util.SC;

public class EditableServerSideDataSourceSample extends ShowcasePanel {
    private static final String DESCRIPTION = "This example demonstrates a DataSource whose definition is stored "+
        "in a SQL database rather than in a static *.ds.xml file. The fields of the DataSource can be editted in the "+
        "grid below. Pressing \"Reload\" shows a DynamicForm bound to the modified DataSource. "+
        "<p>"+
        "This pattern can be used to allow end users to dynamically change the definition of DataSources in an application. "+
        "For example, add new fields, or add additional validators to existing fields.";

   	private DynamicForm dsFieldForm;
    private VLayout formContainer;
	
    private Object[] updateDataSource() {
        DataSource.load("dynamicDS", new Function() {
                @Override
                public void execute() {
		    if (dsFieldForm != null) {
                        formContainer.removeMember(dsFieldForm);
                        dsFieldForm.clear();
                    }
                    dsFieldForm = new DynamicForm();
                    dsFieldForm.setDataSource(DataSource.get("dynamicDS"));
                    formContainer.addMember(dsFieldForm, 0);
                }
            }, true);
        return new Object[0];
    }
	
    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            EditableServerSideDataSourceSample panel = new EditableServerSideDataSourceSample();
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

   	    DMI.call("GeneratorSetup", 
			    "com.smartgwt.sample.showcase.server.customDataSource.GeneratorSetup",
			    "setupGenerator", null, updateDataSource());

        DataSource dynamicDSFields = DataSource.get("dynamicDSFields");

		final ListGrid dsFieldEditList = new ListGrid();
		dsFieldEditList.setWidth100();
		dsFieldEditList.setHeight(224);
		dsFieldEditList.setAlternateRecordStyles(true);
		// use server-side dataSource so edits are retained across page transitions
		dsFieldEditList.setDataSource(dynamicDSFields);
		dsFieldEditList.setAutoFetchData(true);
		dsFieldEditList.setCanEdit(true);
		dsFieldEditList.setCanRemoveRecords(true);
		dsFieldEditList.setEditEvent(ListGridEditEvent.CLICK);
		dsFieldEditList.setListEndEditAction(RowEndEditAction.NEXT);
		
		IButton editNew = new IButton();
		editNew.setTitle("Edit New");
		editNew.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dsFieldEditList.startEditingNew();
			}
		});
		IButton reload = new IButton();
		reload.setTitle("Reload");
		reload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateDataSource();
			}
		});
		
		VLayout leftContainer = new VLayout();
		leftContainer.setPadding(10);
		leftContainer.setMembersMargin(10);
		leftContainer.addMember(dsFieldEditList);
		leftContainer.addMember(editNew);
		
		formContainer = new VLayout();
		formContainer.setWidth(400);
		formContainer.setPadding(10);
		formContainer.setMembersMargin(10);
		formContainer.setOverflow(Overflow.AUTO);
		formContainer.addMember(reload);
		formContainer.setIsGroup(true);
		formContainer.setGroupTitle("Form bound to fields");
		
		HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(10);
		hLayout.addMember(leftContainer);
		hLayout.addMember(formContainer);
		
		VLayout dataView = new VLayout();
		dataView.setOverflow(Overflow.HIDDEN);
		dataView.setWidth100();
		dataView.setHeight100();
		dataView.addMember(hLayout);

        return dataView;
        
    }

    public String getIntro() {
        return DESCRIPTION;
    }
    
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("dynamicDSFields.ds.xml", XML, "source/dynamicDSFields.ds.xml.html", true),
            new SourceEntity("server/customDataSource/GeneratorSetup.java", JAVA, "source/dataintegration/java/datasource/GeneratorSetup.java.html", true)
        };
    }
}
