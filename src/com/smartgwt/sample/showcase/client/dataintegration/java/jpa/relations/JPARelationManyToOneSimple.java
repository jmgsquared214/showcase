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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class JPARelationManyToOneSimple extends ShowcasePanel {

    private static final String DESCRIPTION = "Smart GWT handles JPA Many-to-One relationships transparently (such as Cities within " +
            "a Country). Simply declare a <code>foreignKey</code> field on the \"cityManyToOneSimpleJPA\" DataSource to indicate the " +
            "need to use the related JPA entity \"countryManyToOneSimpleJPA\"." +
            "<P>" +
            "The grid below shows Cities, but the Country name is automatically shown even though the " +
            "\"countryName\" field is stored in the related JPA entity \"countryManyToOneSimpleJPA\".  Any fields from any number of " +
            "related entities can be automatically loaded this way." +
            "<P>" +
            "Click to edit and change the Country of a City.  The list of Countries is automatically " +
            "loaded from the related JPA entity, along with their IDs." +
            "<P>" +
            "Changing the Country of a City sends the ID of the new Country back to the server, and " +
            "Smart GWT automatically makes all the required JPA calls to persist the change. No " +
            "server-side code needs to be written beyond the JPA beans themselves and their " +
            "annotations.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            JPARelationManyToOneSimple panel = new JPARelationManyToOneSimple();
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
        final ListGrid listGrid = new ListGrid();
        listGrid.setDataSource(DataSource.get("cityManyToOneSimpleJPA"));
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setShowFilterEditor(true);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setAutoFetchData(true);
        listGrid.setDataPageSize(50);
        listGrid.setCanEdit(true);
        listGrid.setEditEvent(ListGridEditEvent.CLICK);
        listGrid.setCanRemoveRecords(true);
        listGrid.setFields(new ListGridField("cityName", "City"), new ListGridField("country", "Country"));

        IButton newButton = new IButton("Add New");
        newButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                listGrid.startEditingNew();
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(listGrid);
        layout.addMember(newButton);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
    // Do not remove commented lines.
    // Forces GenerateSourceFiles to generate DataURLRecords for both data sources.
    // Generated DataURLRecords are used in 'View source' window for showing data source configuration.
//        DataSource.get("cityManyToOneSimpleJPA");
//        DataSource.get("countryManyToOneSimpleJPA");
        return new SourceEntity[]{
                new SourceEntity("CityManyToOneSimple.java", JAVA, "source/beans/CityManyToOneSimple.java.html", true),
                new SourceEntity("CountryManyToOneSimple.java", JAVA, "source/beans/CountryManyToOneSimple.java.html", true),
                new SourceEntity("META-INF/persistence.xml", XML, "source/ds/common/persistenceManyToOneSimple.xml.html", true)
            };
    }
}
