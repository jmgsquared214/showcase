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

public class HBIncludeFromDynamic extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "Fields can be included from other, related DataSources on-demand, on a screen-specific basis, " +
            "by using the <code>includeFrom</code> attribute on a ListGridField." +
            "<p>" +
            "In the grid below, declarations in the DataSource cause the \"Country Name\" field to appear for " +
            "each City.  However the field \"Country Code\" is included on-demand, just for this grid, by " +
            "declaring the <code>includeFrom</code> attribute.";
    
    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            HBIncludeFromDynamic panel = new HBIncludeFromDynamic();
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

        ListGridField countryCodeField = new ListGridField();
        countryCodeField.setIncludeFrom("countryIncludeFromHB.countryCode");

        listGrid.setFields(new ListGridField("cityName"),
                new ListGridField("country"),
                countryCodeField);

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
