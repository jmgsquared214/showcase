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
package com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.relations;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class HBIncludeFrom extends ShowcasePanel {

    private static final String DESCRIPTION = 
        "Fields can be included from other, related DataSources by just declaring the name of the " +
        "DataSource and field to include, using the <code>includeFrom</code> property." + 
        "<p>" +
        "In the example below, a DataSource that stores Cities is shown.  It includes the " +
        "field \"Country Name\" from a related DataSource that stores Countries.  It also " +
        "includes the field \"Continent\" from an <i>indirectly</i> related DataSource that stores " +
        "Continents.  At the database layer, this related data is all being fetched using an efficient " +
        "SQL join." +
        "<p>" +
        "Click to edit the \"Country Name\" field for a row - this field has been configured as a " +
        "ComboBox that edits which Country a City belongs to.  Try shifting a City to a Country on a " +
        "different Continent, and note how the \"Continent\" field automatically updates.  This happens " +
        "because <code>includeFrom</code> declarations are automatically applied when the server returns " +
        "data to update the UI.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            HBIncludeFrom panel = new HBIncludeFrom();
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
        listGrid.setDataSource(DataSource.get("cityIncludeFromHB"));
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setShowFilterEditor(true);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setAutoFetchData(true);
        listGrid.setDataPageSize(50);
        listGrid.setCanEdit(true);
        listGrid.setEditEvent(ListGridEditEvent.CLICK);
        listGrid.setFields(new ListGridField("cityName"),
                new ListGridField("country"),
                new ListGridField("continentName"));

        VLayout layout = new VLayout(15);
        layout.addMember(listGrid);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
    // Do not remove commented lines.
    // Forces GenerateSourceFiles to generate DataURLRecords for both data sources.
    // Generated DataURLRecords are used in 'View source' window for showing data source configuration.
//        DataSource.get("cityIncludeFromHB");
//        DataSource.get("countryIncludeFromHB");
//        DataSource.get("continentIncludeFromHB");
        return new SourceEntity[]{
                new SourceEntity("CityManyToOneSimple.java", JAVA, "source/beans/CityManyToOneSimple.java.html", true),
                new SourceEntity("CountryManyToOneSimple.java", JAVA, "source/beans/CountryManyToOneSimple.java.html", true),
                new SourceEntity("ContinentManyToOneSimple.java", JAVA, "source/beans/ContinentManyToOneSimple.java.html", true)
            };
    }
}
