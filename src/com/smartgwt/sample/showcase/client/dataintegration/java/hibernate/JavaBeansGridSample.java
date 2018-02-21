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

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class JavaBeansGridSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Smart GWT Server DataSource operations can be fulfilled by returning Java " +
            "Beans (aka EJBs or POJOs) from existing business logic.</p>" +
            "<p>When Smart GWT's <code>DSResponse.setData()</code> Server API is called, Java objects are automatically translated to JavaScript, transmitted to the browser, " +
            "and provided to the requesting component. See the sample implementation of the \"fetch\" operation in <code>SupplyItemFetch.java</code>.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            JavaBeansGridSample panel = new JavaBeansGridSample();
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

    public Canvas getViewPanel() {
        ListGrid boundGrid = new ListGrid();
        boundGrid.setDataSource(DataSource.get("supplyItemDMI"));
        boundGrid.setCanEdit(true);
        boundGrid.setWidth100();
        boundGrid.setHeight(300);

        Criteria filterCriteria = new Criteria();
        filterCriteria.addCriteria("itemName", "account");
        boundGrid.filterData(filterCriteria);

        return boundGrid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
                return new SourceEntity[]{
                new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true),
                new SourceEntity("server/SupplyItemDMI.java", JAVA, "source/ds/si-dmi/SupplyItemDMI.java.html", true),
                new SourceEntity("server/SupplyItem.java", JAVA, "source/beans/SupplyItem.java.html", true),
                new SourceEntity("SupplyItemStore.java", JAVA, "source/ds/si-dmi/SupplyItemStore.java.html", true)};
    }
}