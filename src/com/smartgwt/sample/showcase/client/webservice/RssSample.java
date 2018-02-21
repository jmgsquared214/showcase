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

package com.smartgwt.sample.showcase.client.webservice;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceLinkField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class RssSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Smart GWT Server includes an HTTP proxy servlet which " +
            "supports contacting REST and WSDL web services as though they were hosted by a local web server, avoiding" +
            " the \"same origin policy\" restriction which normally prevents web applications from accessing remote services.</p>" +
            "<p>The proxy is used automatically whenever an attempt is made to contact a URL on another host - no special code " +
            "is needed. In this example, a DataSource is configured to download <a href=\"http://rss.slashdot.org/Slashdot/slashdot\" target=\"_blank\">Slashdot's RSS feed</a>, " +
            "with no server-side code or proxy configuration required.</p>" +
            "<p>Configuration files allow for restricting proxying to specific services that users are allowed to contact through your application.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            RssSample panel = new RssSample();
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
        DataSource dataSource = new DataSource();
        dataSource.setDataURL("http://rss.slashdot.org/Slashdot/slashdot");
        dataSource.setRecordXPath("//item | //default:item");

        DataSourceTextField titleField = new DataSourceTextField("title", "Title");
        DataSourceLinkField linkField = new DataSourceLinkField("link", "Link");

        dataSource.setFields(titleField, linkField);

        ListGrid grid = new ListGrid();
        grid.setAutoFetchData(true);
        grid.setHeight(200);
        grid.setWidth100();
        grid.setDataSource(dataSource);

        return grid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}