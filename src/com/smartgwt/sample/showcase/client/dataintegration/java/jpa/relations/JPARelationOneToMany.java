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
package com.smartgwt.sample.showcase.client.dataintegration.java.jpa.relations;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class JPARelationOneToMany extends ShowcasePanel {

    private static final String DESCRIPTION = "Smart GWT handles JPA One-to-Many relationships transparently, such as a Country which has " +
            "multiple Cities.  Simply declare a collection field (<code>multiple:true</code>) on the \"countryManyToOneSimpleJPA\" " +
            "DataSource to indicate that the list of Cities needs to be loaded." +
            "<P>" +
            "Click on a Country below. Its list of Cities are shown without a separate trip to the " +
            "server.  Cities can be now edited in the lower grid." +
            "<P>" +
            "When data is saved, all changes to the Country and its Cities are sent in one save " +
            "request, and Smart GWT automatically makes all the required JPA calls to persist the " +
            "changes. No server-side code needs to be written beyond the JPA beans themselves and " +
            "their annotations.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            JPARelationOneToMany panel = new JPARelationOneToMany();
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
        Label countryListLabel = new Label();
        countryListLabel.setContents("Country list");
        countryListLabel.setWidth("90%");
        countryListLabel.setHeight(25);
        countryListLabel.setBaseStyle("exampleSeparator");

        ListGrid countryList = new ListGrid();
        countryList.setDataSource(DataSource.get("countryOneToManyJPA"));
        countryList.setWidth(700);
        countryList.setHeight(150);
        countryList.setAlternateRecordStyles(true);
        countryList.setAutoFetchData(true);
        countryList.setDataPageSize(50);
        countryList.setCanRemoveRecords(true);

        IButton newCountryButton = new IButton("Add New Country");
        newCountryButton.setWidth(120);

        VLayout countryListLayout = new VLayout(5);
        countryListLayout.addMember(countryListLabel);
        countryListLayout.addMember(countryList);
        countryListLayout.addMember(newCountryButton);

        Label countryLabel = new Label();
        countryLabel.setContents("Country edit");
        countryLabel.setWidth("90%");
        countryLabel.setHeight(25);
        countryLabel.setBaseStyle("exampleSeparator");

        final DynamicForm countryForm = new DynamicForm();
        countryForm.setDataSource(DataSource.get("countryOneToManyJPA"));

        final ListGrid cityList = new ListGrid();
        cityList.setDataSource(DataSource.get("cityOneToManyJPA"));
        cityList.setWidth(700);
        cityList.setHeight(224);
        cityList.setAlternateRecordStyles(true);
        cityList.setSaveLocally(true);
        cityList.setSaveByCell(true);
        cityList.setShowFilterEditor(true);
        cityList.setCanEdit(true);
        cityList.setEditEvent(ListGridEditEvent.CLICK);
        cityList.setCanRemoveRecords(true);

        IButton newCityButton = new IButton("Add New City");
        newCityButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                cityList.startEditingNew();
            }
        });

        IButton saveCityButton = new IButton("Save");
        saveCityButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryForm.setValue("cities", cityList.getRecords());
                countryForm.saveData();
            }
        });

        final VLayout countryEditLayout = new VLayout(5);
        countryEditLayout.addMember(countryLabel);
        countryEditLayout.addMember(countryForm);
        countryEditLayout.addMember(cityList);
        countryEditLayout.addMember(newCityButton);
        countryEditLayout.addMember(saveCityButton);
        countryEditLayout.setDisabled(true);

        VLayout layout = new VLayout(5);
        layout.addMember(countryListLayout);
        layout.addMember(countryEditLayout);

        countryList.addSelectionChangedHandler(new SelectionChangedHandler() {
            public void onSelectionChanged(SelectionEvent event) {
                if (event.getState()) {
                    countryEditLayout.setDisabled(false);
                    cityList.setData(event.getSelectedRecord().getAttributeAsRecordArray("cities"));
                    countryForm.editRecord(event.getSelectedRecord());
                } else {
                    countryEditLayout.setDisabled(true);
                }
            }
        });

        newCountryButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                countryEditLayout.setDisabled(false);
                cityList.setData(new ListGridRecord[0]);
                countryForm.editNewRecord();
            }
        });

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("CityOneToMany.java", JAVA, "source/beans/CityOneToMany.java.html", true),
                new SourceEntity("CountryOneToMany.java", JAVA, "source/beans/CountryOneToMany.java.html", true),
                new SourceEntity("META-INF/persistence.xml", XML, "source/ds/common/persistenceOneToMany.xml.html", true)
            };
    }
}
