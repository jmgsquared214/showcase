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

public class SQLIncludeVia extends ShowcasePanel {
    
    private static final String DESCRIPTION = "A DataSource is sometimes related to another DataSource in <b>two</b> ways, for " +
        "example, a \"moneyTransfer\" DataSource may be related to a \"currency\" DataSource by two <code>foreignKey</code>s - " +
        "one representing the source currency, and one representing the payment currency." +
        "<p/>" +
        "When this happens, you can still use the <code>includeFrom</code> feature to include fields from the related DataSource, " +
        "but you need to use the <code>includeVia</code> property to specify which <code>foreignKey</code> should be used.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            SQLIncludeVia panel = new SQLIncludeVia();
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
        listGrid.setDataSource(DataSource.get("moneyTransfer"));
        listGrid.setWidth(700);
        listGrid.setHeight(224);
        listGrid.setShowFilterEditor(true);
        listGrid.setAlternateRecordStyles(true);
        listGrid.setAutoFetchData(true);
        listGrid.setDataPageSize(50);
        listGrid.setCanEdit(true);
        listGrid.setEditEvent(ListGridEditEvent.CLICK);
        listGrid.setFields(new ListGridField("name"),
                new ListGridField("paymentAmount"),
                new ListGridField("sourceCurrencySymbol"),
                new ListGridField("paymentCurrencySymbol")
        );
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
//        DataSource.get("moneyTransfer");
//        DataSource.get("currency");
        return new SourceEntity[]{
            };
    }
}
