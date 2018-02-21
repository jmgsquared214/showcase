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
package com.smartgwt.sample.showcase.client.dataintegration.java.sql.relations;

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

public class SQLIncludeFromDynamic extends ShowcasePanel {

    private static final String DESCRIPTION = "Fields from related DataSources can be dynamically included " +
            "(without directly declaring them in the DataSource). \"cityIncludeFromSQL\" does not have the declared field \"countryCode\". " +
            "This field is specified in the ListGrid with the <code>includeFrom</code> property pointing to the relevant field " +
            "in the related \"countryIncludeFromSQL\" DataSource. " +
            "For this widget only, the system automatically adds the specified field and retrieves its value.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            SQLIncludeFromDynamic panel = new SQLIncludeFromDynamic();
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
        listGrid.setDataSource(DataSource.get("cityIncludeFromSQL"));
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setShowFilterEditor(true);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setAutoFetchData(true);
        listGrid.setDataPageSize(50);
        listGrid.setCanEdit(true);
        listGrid.setEditEvent(ListGridEditEvent.CLICK);

        ListGridField countryCodeField = new ListGridField();
        countryCodeField.setIncludeFrom("countryIncludeFromSQL.countryCode");

        listGrid.setFields(new ListGridField("cityName"),
                new ListGridField("countryId"),
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
//        DataSource.get("cityIncludeFromSQL");
//        DataSource.get("countryIncludeFromSQL");
//        DataSource.get("continentIncludeFromSQL");
        return new SourceEntity[]{
            };
    }
}
