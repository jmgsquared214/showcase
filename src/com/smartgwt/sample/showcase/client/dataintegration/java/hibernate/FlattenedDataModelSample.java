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

package com.smartgwt.sample.showcase.client.dataintegration.java.hibernate;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class FlattenedDataModelSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows the Smart GWT Server's support for flattening and " +
            "reconstructing hierarchical data, by use of XPaths. The ListGrid below shows each user's \"address\", \"city\" and " +
            "\"state\" as if those fields were part of the user's data. In fact, this address information is held in a separate" +
            " Address bean. This information is extracted from the separate bean at fetch time by the Smart GWT Server, based" +
            " purely on the XPath declarations of those fields in the DataSource.</p>" +
            "<p>More interestingly, the Smart GWT Server is also able to reconstruct the hierarchical data from the flattened " +
            "version, again transparently by use of the XPath. This means that updating the flattened fields in this " +
            "sample - for example, changing a user's city - causes the changes to be correctly persisted automatically.</p>" +
            "<p>Note also that the User bean has a \"password\" attribute which is completely excluded from this " +
            "example. When <code>dropExtraFields</code> is specified on a DataSource, as is done here, Smart GWT Server returns " +
            "just those fields defined in the DataSource. So, as in this example, an existing schema can be used, whilst easily " +
            "retaining tight control over what gets delivered to the client. This includes related entities as well as simple " +
            "attributes.</p>" +
            "<p>Click a record in the grid to see the order's details in the form. Edit the user details and click \"Save Changes\"." +
            " Using the declared XPaths, the Smart GWT Server will populate any changed flattened field back into its correct place" +
            " in the hierarchy, allowing the data provider (Hibernate, in this case) to persist the change.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            FlattenedDataModelSample panel = new FlattenedDataModelSample();
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

        DataSource flatUserDS = DataSource.get("flattenedBeans_flatUserHB");

        final DynamicForm editorForm = new  DynamicForm();
        editorForm.setWidth(280);
        editorForm.setDataSource(flatUserDS);
        editorForm.setWrapItemTitles(false);

        TextItem firstNameItem = new TextItem("firstName", "First Name");
        TextItem surnameItem = new TextItem("surname", "Surname");
        TextItem emailItem = new TextItem("email", "Email address");
        TextItem addressLineItem = new TextItem("addressLine1", "Address Line 1");
        TextItem cityItem = new TextItem("city", "City");
        TextItem stateItem = new TextItem("state", "State");

        editorForm.setItems(firstNameItem, surnameItem, emailItem, addressLineItem, cityItem, stateItem);

        ListGrid userListGrid = new ListGrid();
        userListGrid.setWidth(800);
        userListGrid.setHeight(164);
        userListGrid.setDataSource(flatUserDS);
        userListGrid.setAutoFetchData(true);

        ListGridField firstName = new ListGridField("firstName");
        ListGridField surname = new ListGridField("surname");
        ListGridField email = new ListGridField("email");
        ListGridField addressLine1 = new ListGridField("addressLine1");
        ListGridField city = new ListGridField("city");
        ListGridField state = new ListGridField("state");

        userListGrid.setFields(firstName, surname, email, addressLine1, city, state);

        userListGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
            public void onSelectionChanged(SelectionEvent event) {
                editorForm.editRecord(event.getRecord());
            }
        });

        IButton addUserButton = new IButton("Add User");
        addUserButton.setWidth(120);
        addUserButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                editorForm.editNewRecord();
            }
        });

        IButton saveButton = new IButton("Save Changes");
        saveButton.setWidth(120);
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                editorForm.saveData();
            }
        });

        HLayout hLayout = new HLayout(15);
        hLayout.addMember(editorForm);

        VLayout buttonLayout = new VLayout(15);
        buttonLayout.setMembers(addUserButton, saveButton);
        hLayout.addMember(buttonLayout);

        VLayout layout = new VLayout(15);
        layout.addMember(userListGrid);
        layout.addMember(hLayout);

        return layout;        
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
                new SourceEntity("FlatUser.java", JAVA, "source/beans/FlatUser.java.html", true),
                new SourceEntity("Address.java", JAVA, "source/beans/Address.java.html", true),
                new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true)
        };
    }
}