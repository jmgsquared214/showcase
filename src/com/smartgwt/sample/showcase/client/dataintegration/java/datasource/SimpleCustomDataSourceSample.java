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

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class SimpleCustomDataSourceSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows an entirely custom DataSource. It is created " +
            "by extending <code>BasicDataSource</code> and implementing the four core CRUD methods. In this case, a static " +
            "List of Maps is maintained, that is then initialized with hard-coded data every time the server starts " +
            "(but of course, this code could actually do anything). This approach allows completely custom data operations " +
            "to be simply plugged in to the Smart GWT Server framework.</p>" +
            "<p>Note also that this code deals directly with Java Maps and Lists, without worrying" +
            " about format conversions - even custom code leverages the Smart GWT Server's automatic and transparent translation" +
            " of request data, from JSON to Java and back to JSON.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            SimpleCustomDataSourceSample panel = new SimpleCustomDataSourceSample();
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

        DataSource customDS = DataSource.get("customDataSource_user");

        final ListGrid userList = new ListGrid();
        userList.setWidth(600);
        userList.setHeight(224);
        userList.setDataSource(customDS);
        userList.setCanEdit(true);
        userList.setCanRemoveRecords(true);
        userList.setLeaveScrollbarGap(false);
        userList.setDataFetchMode(FetchMode.LOCAL);
        userList.setAutoFetchData(true);
        userList.setFields(
                new ListGridField("userName"),
                new ListGridField("job"),
                new ListGridField("email"),
                new ListGridField("employeeType"),
                new ListGridField("salary")
                );

        IButton addButton = new IButton("Create User");
        addButton.setWidth(110);
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                userList.startEditingNew();
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(userList);
        layout.addMember(addButton);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
                new SourceEntity("UserDataSource.java", JAVA, "source/datasource/UserDataSource.java.html", true)
        };
    }
}